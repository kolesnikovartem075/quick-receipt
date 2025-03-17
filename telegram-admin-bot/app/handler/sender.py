from aiogram import F, Router
from aiogram.fsm.context import FSMContext
from aiogram.fsm.state import StatesGroup, State
from aiogram.types import Message, CallbackQuery, InlineKeyboardMarkup, InlineKeyboardButton

from app.keyboard.main_keyboard import admin_main_reply_keyboard, add_main_keyboard
from app.keyboard.sender_keyboard import (
    sender_management_menu,
    sender_details_menu,
    create_sender_selection_menu,
    cancel_sender_creation,
    confirm_delete_sender
)

# Create sender router
sender_router = Router()


class SenderCreate(StatesGroup):
    """States for service sender creation flow"""
    name = State()
    phone = State()
    city = State()
    address = State()
    contact_person = State()
    confirmation = State()


@sender_router.callback_query(F.data == "sender_management")
async def sender_management(callback: CallbackQuery):
    """Show sender management menu"""
    text = (
        "👨‍💼 *Управління відправниками* \n\n"
        "Відправники використовуються для автоматичного створення накладних. "
        "Оберіть дію:"
    )

    await callback.message.edit_text(text, reply_markup=sender_management_menu, parse_mode="Markdown")
    await callback.answer()


@sender_router.callback_query(F.data == "create_sender")
async def create_sender_start(callback: CallbackQuery, state: FSMContext):
    """Start service sender creation process"""
    await state.set_state(SenderCreate.name)

    text = add_main_keyboard(
        "📝 *Створення нового відправника* \n\n"
        "Введіть назву відправника або компанії:"
    )

    await callback.message.edit_text(text, reply_markup=cancel_sender_creation, parse_mode="Markdown")
    # Show main reply keyboard
    await callback.message.answer("Для скасування натисніть 'Скасувати'", reply_markup=admin_main_reply_keyboard)
    await callback.answer()


@sender_router.message(F.text == "❌ Скасувати")
@sender_router.callback_query(F.data == "cancel_sender_creation")
async def cancel_creation(message_or_callback, state: FSMContext):
    """Cancel sender creation process"""
    current_state = await state.get_state()

    if current_state is None:
        # No active state, ignore
        return

    await state.clear()

    if isinstance(message_or_callback, CallbackQuery):
        await message_or_callback.message.edit_text(
            "❌ Створення відправника скасовано.",
            reply_markup=sender_management_menu
        )
        await message_or_callback.answer()
    else:
        await message_or_callback.answer(
            "❌ Створення відправника скасовано.",
            reply_markup=sender_management_menu
        )


@sender_router.message(SenderCreate.name)
async def process_sender_name(message: Message, state: FSMContext):
    """Process sender name input"""
    await state.update_data(name=message.text)
    await state.set_state(SenderCreate.phone)

    await message.answer("Введіть номер телефону відправника:")


@sender_router.message(SenderCreate.phone)
async def process_sender_phone(message: Message, state: FSMContext):
    """Process sender phone input"""
    # Basic phone validation
    phone = message.text.strip()

    # Simplified validation, you might want more comprehensive checks
    if not (phone.startswith('+') and len(phone) >= 10 and phone.replace('+', '').isdigit()):
        await message.answer("❌ Неправильний формат номера телефону. Спробуйте ще раз (наприклад, +380XXXXXXXXX):")
        return

    await state.update_data(phone=phone)
    await state.set_state(SenderCreate.city)

    await message.answer("Введіть місто відправника:")


@sender_router.message(SenderCreate.city)
async def process_sender_city(message: Message, state: FSMContext):
    """Process sender city input"""
    await state.update_data(city=message.text)
    await state.set_state(SenderCreate.address)

    await message.answer("Введіть адресу відправника:")


@sender_router.message(SenderCreate.address)
async def process_sender_address(message: Message, state: FSMContext):
    """Process sender address input"""
    await state.update_data(address=message.text)
    await state.set_state(SenderCreate.contact_person)

    await message.answer("Введіть ім'я контактної особи:")


@sender_router.message(SenderCreate.contact_person)
async def process_contact_person(message: Message, state: FSMContext):
    """Process contact person input and show confirmation"""
    await state.update_data(contact_person=message.text)
    await state.set_state(SenderCreate.confirmation)

    data = await state.get_data()

    confirmation_text = (
        "📋 *Підтвердження даних відправника* \n\n"
        f"Назва: {data['name']}\n"
        f"Телефон: {data['phone']}\n"
        f"Місто: {data['city']}\n"
        f"Адреса: {data['address']}\n"
        f"Контактна особа: {data['contact_person']}\n\n"
        "Все правильно? Підтвердіть створення відправника."
    )

    confirm_keyboard = InlineKeyboardMarkup(inline_keyboard=[
        [
            InlineKeyboardButton(text="✅ Підтвердити", callback_data="confirm_sender"),
            InlineKeyboardButton(text="❌ Скасувати", callback_data="cancel_sender_creation")
        ]
    ])

    await message.answer(confirmation_text, reply_markup=confirm_keyboard, parse_mode="Markdown")


@sender_router.callback_query(SenderCreate.confirmation, F.data == "confirm_sender")
async def confirm_sender_creation(callback: CallbackQuery, state: FSMContext):
    """Process sender confirmation and create service sender in database"""
    data = await state.get_data()

    # Create service_sender object for API call
    service_sender = {
        "name": data['name'],
        "phone": data['phone'],
        "city": data['city'],
        "address": data['address'],
        "contactPerson": data['contact_person']
    }

    # Call API to create service sender
    # result = await create_service_sender(service_sender)

    # For demonstration, mock the API call
    # In production, you would call your actual API
    sender_id = 123  # This would come from the API response
    success = True  # This would be based on API response

    if success:
        await callback.message.edit_text(
            f"✅ Відправника \"{data['name']}\" успішно створено!\n\n"
            "Тепер ви можете використовувати цього відправника при створенні накладних.",
            reply_markup=sender_management_menu
        )
    else:
        await callback.message.edit_text(
            "❌ Помилка при створенні відправника. Спробуйте ще раз.",
            reply_markup=sender_management_menu
        )

    await state.clear()
    await callback.answer()


@sender_router.callback_query(F.data == "list_senders")
async def list_senders(callback: CallbackQuery):
    """List all service senders"""
    # Call API to get service senders
    # senders = await get_service_senders()

    # Mock data for demonstration
    senders = [
        {"id": 1, "name": "ТОВ Рога і Копита", "phone": "+380991234567"},
        {"id": 2, "name": "ФОП Іванов", "phone": "+380671234567"}
    ]

    text = "📋 *Список відправників для накладних* \n\n"

    if not senders:
        text += "Немає збережених відправників."
        await callback.message.edit_text(text, reply_markup=sender_management_menu, parse_mode="Markdown")
    else:
        await callback.message.edit_text(text, reply_markup=create_sender_selection_menu(senders),
                                         parse_mode="Markdown")

    await callback.answer()


@sender_router.callback_query(F.data.startswith("view_sender_"))
async def view_sender(callback: CallbackQuery):
    """View service sender details"""
    sender_id = int(callback.data.split("_")[-1])

    # Call API to get service sender details
    # sender = await get_service_sender_by_id(sender_id)

    # Mock data for demonstration
    sender = {
        "id": sender_id,
        "name": "ТОВ Рога і Копита" if sender_id == 1 else "ФОП Іванов",
        "phone": "+380991234567" if sender_id == 1 else "+380671234567",
        "city": "Київ",
        "address": "вул. Хрещатик, 1",
        "contactPerson": "Іван Петров"
    }

    text = (
        f"👨‍💼 *Інформація про відправника* \n\n"
        f"Назва: {sender['name']}\n"
        f"Телефон: {sender['phone']}\n"
        f"Місто: {sender['city']}\n"
        f"Адреса: {sender['address']}\n"
        f"Контактна особа: {sender['contactPerson']}\n"
    )

    await callback.message.edit_text(text, reply_markup=sender_details_menu(sender_id), parse_mode="Markdown")
    await callback.answer()


@sender_router.callback_query(F.data.startswith("delete_sender_"))
async def delete_sender_confirmation(callback: CallbackQuery):
    """Show delete confirmation for service sender"""
    sender_id = int(callback.data.split("_")[-1])

    text = (
        "🗑️ *Видалення відправника* \n\n"
        "Ви впевнені, що хочете видалити цього відправника? "
        "Ця дія не може бути скасована.\n\n"
        "⚠️ Увага: Якщо цей відправник використовується у активних накладних, "
        "його видалення може спричинити проблеми."
    )

    await callback.message.edit_text(text, reply_markup=confirm_delete_sender(sender_id), parse_mode="Markdown")
    await callback.answer()


@sender_router.callback_query(F.data.startswith("confirm_delete_sender_"))
async def confirm_delete_sender_handler(callback: CallbackQuery):
    """Process service sender deletion confirmation"""
    sender_id = int(callback.data.split("_")[-1])

    # Call API to delete service sender
    # success = await delete_service_sender(sender_id)

    # For demonstration, mock the API call
    success = True

    if success:
        await callback.message.edit_text(
            "✅ Відправника успішно видалено.",
            reply_markup=sender_management_menu
        )
    else:
        await callback.message.edit_text(
            "❌ Помилка при видаленні відправника. Можливо, він використовується в активних накладних.",
            reply_markup=sender_management_menu
        )

    await callback.answer()


@sender_router.callback_query(F.data.startswith("edit_sender_"))
async def edit_sender_start(callback: CallbackQuery, state: FSMContext):
    """Start editing service sender"""
    sender_id = int(callback.data.split("_")[-1])

    # Call API to get service sender details
    # sender = await get_service_sender_by_id(sender_id)

    # Mock data for demonstration
    sender = {
        "id": sender_id,
        "name": "ТОВ Рога і Копита" if sender_id == 1 else "ФОП Іванов",
        "phone": "+380991234567" if sender_id == 1 else "+380671234567",
        "city": "Київ",
        "address": "вул. Хрещатик, 1",
        "contactPerson": "Іван Петров"
    }

    # Store existing data and sender ID
    await state.update_data(
        sender_id=sender_id,
        name=sender["name"],
        phone=sender["phone"],
        city=sender["city"],
        address=sender["address"],
        contact_person=sender["contactPerson"]
    )

    # Start edit flow
    await state.set_state(SenderCreate.name)

    text = (
        "✏️ *Редагування відправника* \n\n"
        "Введіть нову назву відправника або компанії "
        f"(поточна: {sender['name']}):"
    )

    cancel_edit = InlineKeyboardMarkup(inline_keyboard=[
        [InlineKeyboardButton(text="❌ Скасувати редагування", callback_data=f"view_sender_{sender_id}")]
    ])

    await callback.message.edit_text(text, reply_markup=cancel_edit, parse_mode="Markdown")
    await callback.answer()


@sender_router.message(F.text == "👨‍💼 Відправники")
async def sender_management_from_reply(message: Message):
    """Handle sender management from reply keyboard"""
    text = (
        "👨‍💼 *Управління відправниками* \n\n"
        "Відправники використовуються для автоматичного створення накладних. "
        "Оберіть дію:"
    )

    await message.answer(text, reply_markup=sender_management_menu, parse_mode="Markdown")
