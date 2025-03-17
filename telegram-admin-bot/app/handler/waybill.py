from aiogram import F, Router
from aiogram.fsm.context import FSMContext
from aiogram.fsm.state import StatesGroup, State
from aiogram.types import Message, CallbackQuery, InlineKeyboardMarkup, InlineKeyboardButton
from app.keyboard.order_keyboard import (
    back_to_order_management,
    waybill_sender_selection
)
from app.keyboard.main_keyboard import admin_main_reply_keyboard, add_main_keyboard

from app.client.APIClient import get_service_senders, get_service_sender_by_id, create_waybill

# Create waybill router
waybill_router = Router()


class CreateWaybill(StatesGroup):
    """States for waybill creation process"""
    waiting_for_sender = State()
    waiting_for_description = State()
    waiting_for_confirmation = State()


@waybill_router.callback_query(F.data.startswith("create_waybill_"))
async def create_waybill_start(callback: CallbackQuery, state: FSMContext):
    """Start waybill creation process with existing user data"""
    order_id = int(callback.data.split("_")[-1])
    await state.update_data(order_id=order_id)

    # Call API to get order with user details
    # order_with_user = await get_order_with_user_details(order_id)

    # Mock order data including user details
    order_with_user = {
        "id": order_id,
        "user": {
            "id": 1,
            "first_name": "Іван",
            "last_name": "Петренко",
            "phone_number": "+380991234567",
            "post_office": {
                "city_description": "Київ",
                "description": "Відділення №12"
            }
        },
        "description": "iPhone 13 чохол, чорний",
        "status": "NEW"
    }

    # Store recipient info from user data
    await state.update_data(
        recipient_name=f"{order_with_user['user']['first_name']} {order_with_user['user']['last_name']}",
        recipient_phone=order_with_user['user']['phone_number'],
        recipient_address=f"{order_with_user['user']['post_office']['city_description']}, {order_with_user['user']['post_office']['description']}",
        order_description=order_with_user['description']
    )

    # Call API to get service senders
    # senders = await get_service_senders()

    # Mock senders data
    senders = [
        {"id": 1, "name": "ТОВ Рога і Копита", "phone": "+380991234567"},
        {"id": 2, "name": "ФОП Іванов", "phone": "+380671234567"}
    ]

    if not senders:
        # No senders available, redirect to create one
        text = (
            "❌ У вас немає налаштованих відправників.\n\n"
            "Спершу потрібно створити відправника для створення накладної."
        )

        create_sender_button = InlineKeyboardMarkup(inline_keyboard=[
            [InlineKeyboardButton(text="➕ Створити відправника", callback_data="create_sender")],
            [InlineKeyboardButton(text="🔙 Назад", callback_data=f"view_order_{order_id}")]
        ])

        await callback.message.edit_text(text, reply_markup=create_sender_button)
    else:
        # Ask to select a sender
        await state.set_state(CreateWaybill.waiting_for_sender)

        text = "👨‍💼 Оберіть відправника для створення накладної:"

        await callback.message.edit_text(text, reply_markup=waybill_sender_selection(order_id, senders))

    await callback.answer()


@waybill_router.callback_query(CreateWaybill.waiting_for_sender, F.data.startswith("select_sender_"))
async def process_sender_selection(callback: CallbackQuery, state: FSMContext):
    """Process sender selection and proceed to confirmation"""
    data = callback.data.split("_")
    sender_id = int(data[2])
    order_id = int(data[3])

    await state.update_data(sender_id=sender_id)

    # Get sender details
    # sender = await get_service_sender_by_id(sender_id)

    # Mock sender data
    sender = {
        "id": sender_id,
        "name": "ТОВ Рога і Копита" if sender_id == 1 else "ФОП Іванов",
        "phone": "+380991234567" if sender_id == 1 else "+380671234567",
        "city": "Київ",
        "address": "вул. Хрещатик, 1",
    }

    # Move directly to confirmation with all data available
    await state.set_state(CreateWaybill.waiting_for_confirmation)

    data = await state.get_data()

    # Use order description as package description by default
    await state.update_data(description=data.get('order_description', f"Замовлення #{order_id}"))

    # Prepare confirmation message with all data
    data = await state.get_data()  # Get updated data

    confirmation_text = (
        "📋 *Створення накладної* \n\n"
        f"Замовлення: #{order_id}\n\n"
        f"👨‍💼 *Відправник:*\n"
        f"Назва: {sender['name']}\n"
        f"Телефон: {sender['phone']}\n"
        f"Адреса: {sender['city']}, {sender['address']}\n\n"
        f"👤 *Отримувач:*\n"
        f"Ім'я: {data['recipient_name']}\n"
        f"Телефон: {data['recipient_phone']}\n"
        f"Адреса: {data['recipient_address']}\n\n"
        f"📝 *Опис вантажу:* {data['description']}\n\n"
        "Все правильно? Підтвердіть створення накладної."
    )

    confirm_keyboard = InlineKeyboardMarkup(inline_keyboard=[
        [
            InlineKeyboardButton(text="✅ Створити накладну", callback_data="confirm_waybill"),
            InlineKeyboardButton(text="❌ Скасувати", callback_data=f"view_order_{order_id}")
        ],
        [
            InlineKeyboardButton(text="✏️ Змінити опис вантажу", callback_data="edit_description")
        ]
    ])

    await callback.message.edit_text(confirmation_text, reply_markup=confirm_keyboard, parse_mode="Markdown")
    await callback.answer()


@waybill_router.callback_query(CreateWaybill.waiting_for_confirmation, F.data == "edit_description")
async def edit_description(callback: CallbackQuery, state: FSMContext):
    """Allow editing package description only"""
    await state.set_state(CreateWaybill.waiting_for_description)

    text = "Введіть опис вантажу:"

    await callback.message.edit_text(text)
    await callback.answer()


@waybill_router.message(CreateWaybill.waiting_for_description)
async def process_description(message: Message, state: FSMContext):
    """Process package description update and return to confirmation"""
    await state.update_data(description=message.text)
    await state.set_state(CreateWaybill.waiting_for_confirmation)

    data = await state.get_data()
    order_id = data["order_id"]
    sender_id = data["sender_id"]

    # Get sender details
    # sender = await get_service_sender_by_id(sender_id)

    # Mock sender data
    sender = {
        "id": sender_id,
        "name": "ТОВ Рога і Копита" if sender_id == 1 else "ФОП Іванов",
        "phone": "+380991234567" if sender_id == 1 else "+380671234567",
        "city": "Київ",
        "address": "вул. Хрещатик, 1",
    }

    confirmation_text = (
        "📋 *Створення накладної* \n\n"
        f"Замовлення: #{order_id}\n\n"
        f"👨‍💼 *Відправник:*\n"
        f"Назва: {sender['name']}\n"
        f"Телефон: {sender['phone']}\n"
        f"Адреса: {sender['city']}, {sender['address']}\n\n"
        f"👤 *Отримувач:*\n"
        f"Ім'я: {data['recipient_name']}\n"
        f"Телефон: {data['recipient_phone']}\n"
        f"Адреса: {data['recipient_address']}\n\n"
        f"📝 *Опис вантажу:* {data['description']}\n\n"
        "Все правильно? Підтвердіть створення накладної."
    )

    confirm_keyboard = InlineKeyboardMarkup(inline_keyboard=[
        [
            InlineKeyboardButton(text="✅ Створити накладну", callback_data="confirm_waybill"),
            InlineKeyboardButton(text="❌ Скасувати", callback_data=f"view_order_{order_id}")
        ],
        [
            InlineKeyboardButton(text="✏️ Змінити опис вантажу", callback_data="edit_description")
        ]
    ])

    await message.answer(confirmation_text, reply_markup=confirm_keyboard, parse_mode="Markdown")


@waybill_router.callback_query(CreateWaybill.waiting_for_confirmation, F.data == "confirm_waybill")
async def confirm_waybill(callback: CallbackQuery, state: FSMContext):
    """Process waybill confirmation and create waybill"""
    data = await state.get_data()
    order_id = data["order_id"]

    # Prepare waybill data for API
    waybill_data = {
        "order_id": order_id,
        "sender_id": data["sender_id"],
        "recipient_name": data["recipient_name"],
        "recipient_phone": data["recipient_phone"],
        "recipient_address": data["recipient_address"],
        "description": data["description"]
    }

    # Call API to create waybill
    # waybill_result = await create_waybill(waybill_data)

    # Mock API response
    waybill_result = {
        "success": True,
        "tracking_number": "59000123456789",
        "estimated_delivery": "2023-05-05"
    }

    if waybill_result["success"]:
        # Update order with tracking number and change status to SHIPPED
        # await update_order_status(order_id, "SHIPPED", tracking_number=waybill_result["tracking_number"])

        text = (
            "✅ *Накладну успішно створено!* \n\n"
            f"Номер накладної: {waybill_result['tracking_number']}\n"
            f"Орієнтовна дата доставки: {waybill_result['estimated_delivery']}\n\n"
            f"Статус замовлення №{order_id} змінено на 'Відправлено'."
        )

        await callback.message.edit_text(text, reply_markup=back_to_order_management, parse_mode="Markdown")
    else:
        text = (
            "❌ *Помилка при створенні накладної* \n\n"
            "Будь ласка, перевірте введені дані та спробуйте ще раз."
        )

        retry_keyboard = InlineKeyboardMarkup(inline_keyboard=[
            [InlineKeyboardButton(text="🔄 Спробувати ще раз", callback_data=f"create_waybill_{order_id}")],
            [InlineKeyboardButton(text="🔙 Назад", callback_data=f"view_order_{order_id}")]
        ])

        await callback.message.edit_text(text, reply_markup=retry_keyboard, parse_mode="Markdown")

    await state.clear()
    await callback.answer()
