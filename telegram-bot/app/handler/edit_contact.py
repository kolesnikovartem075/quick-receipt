import re
from aiogram import Router, F
from aiogram.fsm.context import FSMContext
from aiogram.fsm.state import StatesGroup, State
from aiogram.types import Message, CallbackQuery

from app.client.APIClient import update_user_contact, fetch_user_contact, fetch_cities, fetch_warehouses
from app.config import account_id
from app.handler.order import show_user_details
from app.keyboard.keyboard import edit_contact, inline_cities, inline_warehouses

edit_contact_router = Router()


class EditContact(StatesGroup):
    first_name = State()
    last_name = State()
    phone_number = State()
    city = State()
    city_ref = State()
    nova_post_address = State()
    warehouse_ref = State()
    contact_id = State()
    user_contact_id = State()
    user_id = State()
    message_id = State()  # For tracking the status message


@edit_contact_router.callback_query(F.data == "edit_user")
async def edit_user_contact(callback: CallbackQuery, state: FSMContext):
    """Display edit options for user contact"""
    # Get current user data
    user_contact = await fetch_user_contact(callback.from_user.id)

    if not user_contact:
        await callback.answer("❌ Не вдалося знайти інформацію про користувача")
        return

    # Store the current contact information in state
    await state.update_data(
        user_id=user_contact.user.id,
        contact_id=user_contact.contact.id,
        user_contact_id=user_contact.id,
        first_name=user_contact.contact.first_name,
        last_name=user_contact.contact.last_name,
        phone_number=user_contact.contact.phone_number,
        city=user_contact.contact.warehouse.city.description,
        city_ref=user_contact.contact.warehouse.city.ref,
        nova_post_address=user_contact.contact.warehouse.description,
        warehouse_ref=user_contact.contact.warehouse.ref
    )

    await callback.answer("Редагування контактних даних")

    # Show current contact info and edit keyboard
    status_message = await callback.message.edit_text(
        await format_edit_status(state),
        reply_markup=edit_contact,
        parse_mode="Markdown"
    )

    # Store the status message ID for future updates
    await state.update_data(message_id=status_message.message_id)


@edit_contact_router.callback_query(F.data == "edit_first_name")
async def edit_first_name(callback: CallbackQuery, state: FSMContext):
    """Start editing first name"""
    await callback.answer()
    await state.set_state(EditContact.first_name)
    await callback.message.answer("Введіть нове ім'я:")


@edit_contact_router.callback_query(F.data == "edit_last_name")
async def edit_last_name(callback: CallbackQuery, state: FSMContext):
    """Start editing last name"""
    await callback.answer()
    await state.set_state(EditContact.last_name)
    await callback.message.answer("Введіть нове прізвище:")


@edit_contact_router.callback_query(F.data == "edit_phone")
async def edit_phone(callback: CallbackQuery, state: FSMContext):
    """Start editing phone number"""
    await callback.answer()
    await state.set_state(EditContact.phone_number)
    await callback.message.answer("Введіть новий номер телефону:")


@edit_contact_router.callback_query(F.data == "edit_city")
async def edit_city(callback: CallbackQuery, state: FSMContext):
    """Start editing city"""
    await callback.answer()
    await state.set_state(EditContact.city)
    await callback.message.answer("Введіть нове місто:")


@edit_contact_router.callback_query(F.data == "edit_warehouse")
async def warehouse(callback: CallbackQuery, state: FSMContext):
    """Start editing warehouse"""
    data = await state.get_data()
    await callback.answer()
    await state.set_state(EditContact.nova_post_address)
    await callback.message.answer(
        f'Поточне місто: {data.get("city")}\n\nВведіть номер або назву відділення Нової Пошти:'
    )


@edit_contact_router.message(EditContact.first_name)
async def first_name(message: Message, state: FSMContext):
    """Process new first name input"""
    await state.update_data(first_name=message.text)

    data = await state.get_data()
    if data.get("first_name") is not message.text:
        await update_status_message(message, state)
    await message.answer("✅ Ім'я збережено. Оберіть інше поле для редагування або збережіть зміни.",
                         reply_markup=edit_contact)


@edit_contact_router.message(EditContact.last_name)
async def last_name(message: Message, state: FSMContext):
    """Process new last name input"""
    await state.update_data(last_name=message.text)
    data = await state.get_data()
    if data.get("last_name") is not message.text:
        await update_status_message(message, state)
    await message.answer("✅ Прізвище збережено. Оберіть інше поле для редагування або збережіть зміни.",
                         reply_markup=edit_contact)


@edit_contact_router.message(EditContact.phone_number)
async def phone_number(message: Message, state: FSMContext):
    """Process new phone number input"""
    phone_number = message.text.strip()
    phone_pattern = r'^\+?[0-9]{10,13}$'

    if not re.match(phone_pattern, phone_number):
        await message.answer('❌ Невірний формат номера телефону. Введіть номер у форматі +380XXXXXXXXX або 0XXXXXXXXX:')
        return

    await state.update_data(phone_number=phone_number)
    data = await state.get_data()
    if data.get("phone_number") is not message.text:
        await update_status_message(message, state)
    await message.answer("✅ Номер телефону збережено. Оберіть інше поле для редагування або збережіть зміни.",
                         reply_markup=edit_contact)


@edit_contact_router.message(EditContact.city)
async def process_city_input(message: Message, state: FSMContext):
    """Process new city input - using the same logic as in registration"""
    cities = await fetch_cities(message.text)

    if not cities or len(cities) == 0:
        await message.answer('❌ Місто не знайдено. Спробуйте ще раз або введіть частину назви міста:')
        return

    if len(cities) == 1:
        city = cities[0]
        await set_city_for_edit(message, state, city.description, city.ref)
    else:
        keyboard = inline_cities(cities[:10])
        await message.answer('Оберіть місто зі списку:', reply_markup=keyboard)


@edit_contact_router.callback_query(lambda c: c.data.startswith('city_'))
async def city_selection(callback: CallbackQuery, state: FSMContext):
    """Process city selection - this is shared between registration and edit"""
    current_state = await state.get_state()

    parts = callback.data.split('|')

    if len(parts) != 2:
        await callback.message.answer('❌ Помилка при виборі міста. Спробуйте ще раз.')
        await callback.answer()
        return

    city_ref = parts[0].split('_')[1]
    city_description = parts[1]

    await callback.message.delete()
    await callback.answer()

    await set_city_for_edit(callback.message, state, city_description, city_ref)


@edit_contact_router.message(EditContact.nova_post_address)
async def nova_post_address(message: Message, state: FSMContext):
    """Process warehouse input - using similar logic to registration but for editing"""
    data = await state.get_data()

    warehouse_request = {
        "cityRef": data.get('city_ref'),
        "findByString": message.text
    }

    warehouses = await fetch_warehouses(warehouse_request)

    if not warehouses or len(warehouses) == 0:
        await message.answer('❌ Відділення не знайдено. Спробуйте ввести інший номер або назву:')
        return

    if len(warehouses) == 1:
        await set_warehouse_for_edit(message, state, warehouses[0])
    else:
        keyboard = inline_warehouses(warehouses[:5])
        await message.answer('Оберіть відділення зі списку:', reply_markup=keyboard)


@edit_contact_router.callback_query(lambda c: c.data.startswith('warehouse_'))
async def process_warehouse_selection(callback: CallbackQuery, state: FSMContext):
    """Process warehouse selection - this is shared between registration and edit"""
    data = await state.get_data()

    warehouse_request = {
        "cityRef": data.get('city_ref'),
        "ref": callback.data.split('_')[1]
    }

    warehouses = await fetch_warehouses(warehouse_request)

    if not warehouses or len(warehouses) != 1:
        await callback.message.answer('❌ Помилка при виборі відділення. Спробуйте ще раз.')
        await callback.answer()
        return

    await callback.message.delete()
    await callback.answer()

    await set_warehouse_for_edit(callback.message, state, warehouses[0])


@edit_contact_router.callback_query(F.data == "save_contact_changes")
async def save_contact_changes(callback: CallbackQuery, state: FSMContext):
    """Save all contact changes at once"""
    await callback.answer("Зберігаємо зміни...")
    data = await state.get_data()

    user_id = data.get("user_id")
    contact_id = data.get("contact_id")
    user_contact_id = data.get("user_contact_id")

    if not user_id or not contact_id or not user_contact_id:
        await callback.message.answer("❌ Помилка: інформація про користувача не знайдена.")
        return

    # Prepare the update payload with all changed fields
    update_data = {
        "contactId": contact_id,
        "accountId": account_id,
        "contactCreateEditDto": {
            "firstName": data.get("first_name"),
            "lastName": data.get("last_name"),
            "phoneNumber": data.get("phone_number"),
            "cityRef": data.get("city_ref"),
            "warehouseRef": data.get("warehouse_ref")
        }
    }

    # Update contact in the API
    user_contact = await update_user_contact(user_id, user_contact_id, update_data)

    if user_contact:
        await callback.message.answer("✅ Контактні дані успішно оновлено!")
        await show_user_details(callback.message, user_contact, state)
    else:
        await callback.message.answer("❌ Помилка при оновленні інформації. Спробуйте ще раз.")


@edit_contact_router.callback_query(F.data == "back_to_order")
async def back_to_order(callback: CallbackQuery, state: FSMContext):
    """Return to order screen without saving changes"""
    await callback.answer("Повертаємось до замовлення...")

    # Get current user data
    data = await state.get_data()
    user_id = data.get("user_id")

    # Fetch user contact and display order details
    user_contact = await fetch_user_contact(user_id)
    if user_contact:
        await show_user_details(callback.message, user_contact, state)
    else:
        await callback.message.answer("❌ Помилка при отриманні даних користувача.")


# Helper functions
async def set_city_for_edit(message, state, city_description, city_ref):
    """Set city data for edit and move to warehouse selection"""
    await state.update_data(city=city_description)
    await state.update_data(city_ref=city_ref)
    data = await state.get_data()
    if data.get("city") is not message.text:
        await update_status_message(message, state)

    await state.set_state(EditContact.nova_post_address)
    await message.answer(
        f'Обрано місто: {city_description}\n\nВведіть номер або назву відділення Нової Пошти:'
    )


async def set_warehouse_for_edit(message, state, warehouse):
    """Set warehouse data for edit and move back to edit menu"""
    await state.update_data(warehouse_ref=warehouse.ref)
    await state.update_data(nova_post_address=warehouse.description)
    data = await state.get_data()
    if data.get("nova_post_address") is not message.text:
        await update_status_message(message, state)

    await message.answer(
        f"✅ Обрано відділення: {warehouse.description}\n\n"
        "Оберіть інше поле для редагування або збережіть зміни.",
        reply_markup=edit_contact
    )


async def format_edit_status(state: FSMContext) -> str:
    """Format the current contact information status"""
    data = await state.get_data()

    return (
        "📋 *Редагування контактних даних:* \n\n"
        f"👤 Ім'я: {data.get('first_name', '❌ Не вказано')}\n"
        f"👤 Прізвище: {data.get('last_name', '❌ Не вказано')}\n"
        f"📍 Місто: {data.get('city', '❌ Не вказано')}\n"
        f"🏢 Відділення Нової Пошти: {data.get('nova_post_address', '❌ Не вказано')}\n"
        f"📞 Телефон: {data.get('phone_number', '❌ Не вказано')}\n\n"
        "Оберіть поле для редагування:"
    )


async def update_status_message(message: Message, state: FSMContext):
    """Update the status message with current contact information"""
    data = await state.get_data()
    message_id = data.get("message_id")

    if message_id:
        await message.bot.edit_message_text(
            await format_edit_status(state),
            chat_id=message.chat.id,
            message_id=int(message_id),
            reply_markup=edit_contact,
            parse_mode="Markdown"
        )
