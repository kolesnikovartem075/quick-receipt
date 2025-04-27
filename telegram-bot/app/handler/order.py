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
    """Display user details and prepare for order creation"""
    text = format_user_details(user_contact)
    await message.answer(text, reply_markup=order_confirmation, parse_mode="Markdown")

    # Save needed data for order processing
    await state.update_data(user_id=user_contact.user.id)
    await state.update_data(account_id=user_contact.user.account_id)
    await state.set_state(Order.description)


@order_router.message(Order.description)
async def process_description(message: Message, state: FSMContext):
    """Handle order description input from user"""
    await state.update_data(description=message.text)
    await message.answer(
        "✅ Опис додано. Натисніть \"Оформити доставку\".",
        reply_markup=order_confirmation
    )


@order_router.callback_query(F.data == 'confirm_order')
async def confirm_order(callback: CallbackQuery, state: FSMContext):
    """Process order confirmation and create order"""
    data = await state.get_data()

    order_payload = get_order_payload(data)
    order = await create_order(order_payload)

    await process_order_result(callback.message, order)

    await callback.message.edit_reply_markup(reply_markup=None)
    await state.clear()


def get_order_payload(data):
    order_payload = {
        "accountId": data["account_id"],
        "description": data.get("description", "Без опису"),
        "userContactId": data["user_id"]
    }
    return order_payload


async def process_order_result(message: Message, order):
    """Handle order creation result and send appropriate message"""
    if not order:
        await message.answer("❌ Помилка при створенні замовлення. Спробуйте ще раз.")
        return

    text = (
        f"🎉 Ваше замовлення створено!\n\n"
        f"🆔 Номер замовлення: {order.id}\n"
        f"📝 Опис: {order.description}\n\n"
        f"Очікуйте підтвердження від адміністрації."
    )

    await message.answer(text)


def format_user_details(user_contact: UserContactRead) -> str:
    """Format user details for display"""
    return (
        "📋 *Ваші дані:* \n\n"
        f"👤 Ім'я: {user_contact.contact.first_name}\n"
        f"👤 Прізвище: {user_contact.contact.last_name}\n"
        f"📍 Місто: {user_contact.contact.warehouse.city.description}\n"
        f"🏢 Відділення Нової Пошти: {user_contact.contact.warehouse.description}\n"
        f"📞 Телефон: {user_contact.contact.phone_number}\n\n"
        "Додайте опис (необов'язково) або підтвердьте замовлення:"
    )
