from aiogram import Router, F
from aiogram.fsm.context import FSMContext
from aiogram.fsm.state import StatesGroup, State
from aiogram.types import Message, CallbackQuery

from app.client.APIClient import create_order
from app.keyboard.keyboard import order_confirmation
from app.model.user_contact import UserContactRead

order_router = Router()


class Order(StatesGroup):
    user_id = State()
    account_id = State()
    description = State()


async def show_user_details(message: Message, user_contact: UserContactRead, state: FSMContext):
    text = (
        "📋 *Ваші дані:* \n\n"
        f"👤 Ім'я: {user_contact.contact.first_name}\n"
        f"👤 Прізвище: {user_contact.contact.last_name}\n"
        f"📍 Місто: {user_contact.contact.warehouse.description}\n"
        f"🏢 Відділення Нової Пошти: {user_contact.contact.warehouse.description}\n"
        f"📞 Телефон: {user_contact.contact.phone_number}\n\n"
        "Додайте опис (необов'язково) або підтвердьте замовлення:"
    )
    await message.answer(text, reply_markup=order_confirmation, parse_mode="Markdown")
    await state.update_data(user_id=user_contact.user.id)
    await state.update_data(account_id=user_contact.user.account_id)
    await state.set_state(Order.description)


@order_router.message(Order.description)
async def process_description(message: Message, state: FSMContext):
    await state.update_data(description=message.text)
    await message.answer("✅ Опис додано. Натисніть \"Підтвердити замовлення\".", reply_markup=order_confirmation)


@order_router.callback_query(F.data == 'confirm_order')
async def confirm_order(callback: CallbackQuery, state: FSMContext):
    data = await state.get_data()
    description = data.get("description", "Без опису")

    order_payload = {
        "accountId": data["account_id"],
        "description": description,
        "user_id": data["user_id"]
    }

    response = await create_order(order_payload)
    if not response.status_code == 201:
        await callback.answer("❌ Помилка при створенні замовлення. Спробуйте ще раз.")
        await state.clear()
        return

    order = response.json()
    text = (
        f"🎉 Ваше замовлення створено!\n\n🆔 Номер замовлення: {order.id}\n📝 Опис: {order.description}\n\n"
        "Очікуйте підтвердження від адміністрації."
    )

    await callback.message.edit_reply_markup(reply_markup=None)
    await callback.message.answer(text)
    await state.clear()
