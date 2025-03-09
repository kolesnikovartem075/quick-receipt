from aiogram import Router, F
from aiogram.fsm.context import FSMContext
from aiogram.fsm.state import StatesGroup, State
from aiogram.types import Message, CallbackQuery

from app.dto.user import UserReadDto
from app.user_confirmation import order_confirmation

order_router = Router()


class Order(StatesGroup):
    waiting_for_description = State()


async def show_user_details(message: Message, user: UserReadDto, state: FSMContext):
    text = (
        "📋 *Ваші дані:* \n\n"
        f"👤 Ім'я: {user.first_name}\n"
        f"👤 Прізвище: {user.last_name}\n"
        f"📍 Місто: {user.post_office.city_description}\n"
        f"🏢 Відділення Нової Пошти: {user.post_office.description}\n"
        f"📞 Телефон: {user.phone_number}\n\n"
        "Додайте опис (необов'язково) або підтвердьте замовлення:"
    )
    await message.answer(text, reply_markup=order_confirmation, parse_mode="Markdown")
    await state.set_state(Order.waiting_for_description)


@order_router.message(Order.waiting_for_description)
async def process_description(message: Message, state: FSMContext):
    await state.update_data(description=message.text)
    await message.answer("✅ Опис додано. Натисніть \"Підтвердити замовлення\".", reply_markup=order_confirmation)


@order_router.callback_query(F.data == 'confirm_order')
async def confirm_order(callback: CallbackQuery, state: FSMContext):
    data = await state.get_data()
    description = data.get("description", "Без опису")

    order_id = 12345

    text = (
        f"🎉 Ваше замовлення створено!\n\n🆔 Номер замовлення: {order_id}\n📝 Опис: {description}\n\n"
        "Очікуйте підтвердження від адміністрації."
    )

    await callback.message.edit_reply_markup(reply_markup=None)
    await callback.message.answer(text)
    await state.clear()
