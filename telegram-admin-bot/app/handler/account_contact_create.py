from aiogram import Router, F
from aiogram.fsm.context import FSMContext
from aiogram.fsm.state import StatesGroup, State
from aiogram.types import Message, CallbackQuery

from app.client.APIClient import fetch_cities, fetch_warehouses, create_account_contact
from app.keyboard import dashboard_keyboard
from app.keyboard.account_contact_create_keyboard import inline_cities, inline_warehouses
from app.widdleware import require_account_id

create_contact_router = Router()


class AccountContactCreation(StatesGroup):
    """States for account contact creation process"""
    api_key = State()
    first_name = State()
    last_name = State()
    phone_number = State()
    city = State()
    city_ref = State()
    nova_post_address = State()
    warehouse_ref = State()


@create_contact_router.callback_query(F.data == "add_account_contact")
@require_account_id
async def start_account_contact_creation(callback: CallbackQuery, state: FSMContext):
    """Start the account contact creation process"""
    await callback.answer()

    data = await state.get_data()
    account_id = data.get("account_id")

    await state.update_data(account_id=account_id)

    await callback.message.edit_text(
        "➕ Додавання нового контакту аккаунта\n\n"
        "Введіть API ключ для цього контакту:",
        reply_markup=dashboard_keyboard.back_to_account_contacts
    )

    await state.set_state(AccountContactCreation.api_key)


@create_contact_router.message(AccountContactCreation.api_key)
async def process_api_key(message: Message, state: FSMContext):
    """Process API key input"""
    api_key = message.text.strip()

    if not api_key:
        await message.answer(
            "❌ API ключ не може бути порожнім. Введіть API ключ:",
            reply_markup=dashboard_keyboard.back_to_account_contacts
        )
        return

    # Save the API key
    await state.update_data(api_key=api_key)

    # Ask for the next piece of information
    await message.answer(
        "Введіть ім'я контакту:",
        reply_markup=dashboard_keyboard.back_to_account_contacts
    )

    # Move to the next state
    await state.set_state(AccountContactCreation.first_name)


@create_contact_router.message(AccountContactCreation.first_name)
async def process_first_name(message: Message, state: FSMContext):
    """Process first name input"""
    first_name = message.text.strip()

    if not first_name:
        await message.answer(
            "❌ Ім'я не може бути порожнім. Введіть ім'я:",
            reply_markup=dashboard_keyboard.back_to_account_contacts
        )
        return

    # Save the first name
    await state.update_data(first_name=first_name)

    # Ask for the next piece of information
    await message.answer(
        "Введіть прізвище контакту:",
        reply_markup=dashboard_keyboard.back_to_account_contacts
    )

    # Move to the next state
    await state.set_state(AccountContactCreation.last_name)


@create_contact_router.message(AccountContactCreation.last_name)
async def process_last_name(message: Message, state: FSMContext):
    """Process last name input"""
    last_name = message.text.strip()

    if not last_name:
        await message.answer(
            "❌ Прізвище не може бути порожнім. Введіть прізвище:",
            reply_markup=dashboard_keyboard.back_to_account_contacts
        )
        return

    # Save the last name
    await state.update_data(last_name=last_name)

    # Ask for the next piece of information
    await message.answer(
        "Введіть номер телефону контакту (формат: +380XXXXXXXXX):",
        reply_markup=dashboard_keyboard.back_to_account_contacts
    )

    # Move to the next state
    await state.set_state(AccountContactCreation.phone_number)


@create_contact_router.message(AccountContactCreation.phone_number)
async def process_phone_number(message: Message, state: FSMContext):
    """Process phone number input"""
    import re

    phone_number = message.text.strip()
    phone_pattern = r'^\+?[0-9]{10,13}$'

    if not re.match(phone_pattern, phone_number):
        await message.answer(
            "❌ Невірний формат номера телефону. Введіть номер у форматі +380XXXXXXXXX або 0XXXXXXXXX:",
            reply_markup=dashboard_keyboard.back_to_account_contacts
        )
        return

    # Save the phone number
    await state.update_data(phone_number=phone_number)

    # Ask for the next piece of information
    await message.answer(
        "Введіть місто контакту:",
        reply_markup=dashboard_keyboard.back_to_account_contacts
    )

    # Move to the next state
    await state.set_state(AccountContactCreation.city)


@create_contact_router.message(AccountContactCreation.city)
async def process_city(message: Message, state: FSMContext):
    """Process city input"""
    city_query = message.text.strip()

    # Fetch cities from API
    cities = await fetch_cities(city_query)

    if not cities or len(cities) == 0:
        await message.answer(
            "❌ Місто не знайдено. Спробуйте ще раз або введіть частину назви міста:",
            reply_markup=dashboard_keyboard.back_to_account_contacts
        )
        return

    if len(cities) == 1:
        # If only one city found, select it automatically
        city = cities[0]
        await set_city(message, state, city.description, city.ref)
    else:
        # If multiple cities found, show inline keyboard for selection
        keyboard = inline_cities(cities[:10])
        await message.answer(
            "Оберіть місто зі списку:",
            reply_markup=keyboard
        )


@create_contact_router.callback_query(lambda c: c.data.startswith('city_'))
async def city_selection(callback: CallbackQuery, state: FSMContext):
    """Handle city selection from inline keyboard"""
    parts = callback.data.split('|')

    if len(parts) != 2:
        await callback.message.answer(
            "❌ Помилка при виборі міста. Спробуйте ще раз.",
            reply_markup=dashboard_keyboard.back_to_account_contacts
        )
        await callback.answer()
        return

    city_ref = parts[0].split('_')[1]
    city_description = parts[1]

    await callback.message.delete()
    await callback.answer()

    await set_city(callback.message, state, city_description, city_ref)


async def set_city(message, state, city_description, city_ref):
    """Set city data and move to warehouse selection"""
    await state.update_data(city=city_description)
    await state.update_data(city_ref=city_ref)
    await state.set_state(AccountContactCreation.nova_post_address)

    await message.answer(
        f'Обрано місто: {city_description}\n\nВведіть номер або назву відділення Нової Пошти:',
        reply_markup=dashboard_keyboard.back_to_account_contacts
    )


@create_contact_router.message(AccountContactCreation.nova_post_address)
async def process_nova_post_address(message: Message, state: FSMContext):
    """Process Nova Poshta warehouse input"""
    warehouse_query = message.text.strip()

    data = await state.get_data()
    if not data.get('city_ref'):
        await message.answer(
            "❌ Помилка: не вибрано місто. Почніть створення контакту знову.",
            reply_markup=dashboard_keyboard.back_to_account_contacts
        )
        await state.clear()
        return

    # Prepare request to fetch warehouses
    warehouse_request = {
        "cityRef": data.get('city_ref'),
        "findByString": warehouse_query
    }

    # Fetch warehouses from API
    warehouses = await fetch_warehouses(warehouse_request)

    if not warehouses or len(warehouses) == 0:
        await message.answer(
            "❌ Відділення не знайдено. Спробуйте ввести інший номер або назву:",
            reply_markup=dashboard_keyboard.back_to_account_contacts
        )
        return

    if len(warehouses) == 1:
        # If only one warehouse found, select it automatically
        await set_warehouse(message, state, warehouses[0])
    else:
        # If multiple warehouses found, show inline keyboard for selection
        keyboard = inline_warehouses(warehouses[:5])
        await message.answer(
            "Оберіть відділення зі списку:",
            reply_markup=keyboard
        )


@create_contact_router.callback_query(lambda c: c.data.startswith('warehouse_'))
async def warehouse_selection(callback: CallbackQuery, state: FSMContext):
    """Handle warehouse selection from inline keyboard"""
    data = await state.get_data()

    parts = callback.data.split('|')
    warehouse_ref = parts[0].split('_')[1]

    warehouse_request = {
        "cityRef": data.get('city_ref'),
        "ref": warehouse_ref
    }

    warehouses = await fetch_warehouses(warehouse_request)

    if not warehouses or len(warehouses) != 1:
        await callback.message.answer(
            "❌ Помилка при виборі відділення. Спробуйте ще раз.",
            reply_markup=dashboard_keyboard.back_to_account_contacts
        )
        await callback.answer()
        return

    await callback.message.delete()
    await callback.answer()

    await set_warehouse(callback.message, state, warehouses[0])


async def set_warehouse(message, state, warehouse):
    """Set warehouse data and confirm all information"""
    await state.update_data(nova_post_address=warehouse.description)
    await state.update_data(warehouse_ref=warehouse.ref)

    # Get all collected data
    data = await state.get_data()

    # Show summary and ask for confirmation
    summary = (
        "📋 *Інформація про контакт:*\n\n"
        f"👤 Ім'я: {data.get('first_name')}\n"
        f"👤 Прізвище: {data.get('last_name')}\n"
        f"📍 Місто: {data.get('city')}\n"
        f"🏢 Відділення: {data.get('nova_post_address')}\n"
        f"📞 Телефон: {data.get('phone_number')}\n"
        f"🔑 API Ключ: {data.get('api_key')}\n\n"
        "Перевірте дані та підтвердіть створення контакту:"
    )

    await message.answer(
        summary,
        parse_mode="Markdown",
        reply_markup=dashboard_keyboard.confirm_action
    )


@create_contact_router.callback_query(F.data == "confirm_action")
@require_account_id
async def confirm_contact_creation(callback: CallbackQuery, state: FSMContext):
    """Handle confirmation of account contact creation"""
    await callback.answer("Створюємо контакт...")

    data = await state.get_data()
    account_id = data.get("account_id")

    account_contact_data = {
        "apiKey": data.get('api_key'),
        "contactCreateEditDto": {
            "accountId": account_id,
            "firstName": data.get('first_name'),
            "lastName": data.get('last_name'),
            "phoneNumber": data.get('phone_number'),
            "cityRef": data.get('city_ref'),
            "warehouseRef": data.get('warehouse_ref')
        }
    }

    try:
        response = await create_account_contact(account_id, account_contact_data)

        if response.status_code == 201:
            # Success message
            await callback.message.edit_text(
                "✅ Контакт аккаунта успішно створено!\n\n"
                f"👤 Ім'я: {data.get('first_name')}\n"
                f"👤 Прізвище: {data.get('last_name')}\n"
                f"📍 Місто: {data.get('city')}\n"
                f"🏢 Відділення: {data.get('nova_post_address')}\n"
                f"📞 Телефон: {data.get('phone_number')}\n"
                f"🔑 API Ключ: {data.get('api_key')}\n\n"
                "Оберіть наступну дію:",
                reply_markup=dashboard_keyboard.account_contact_menu
            )
        else:
            # Error message
            await callback.message.edit_text(
                f"❌ Помилка при створенні контакту: {response.status_code}\n\n"
                "Спробуйте ще раз або поверніться до меню:",
                reply_markup=dashboard_keyboard.back_to_account_contacts
            )
    except Exception as e:
        # Error message
        await callback.message.edit_text(
            f"❌ Помилка при створенні контакту: {str(e)}\n\n"
            "Спробуйте ще раз або поверніться до меню:",
            reply_markup=dashboard_keyboard.back_to_account_contacts
        )

    # Clear state
    await state.clear()


@create_contact_router.callback_query(F.data == "cancel_action")
async def cancel_contact_creation(callback: CallbackQuery, state: FSMContext):
    """Handle cancellation of contact creation"""
    await callback.answer("Скасовано створення контакту")

    # Return to contact management menu
    await callback.message.edit_text(
        "🔄 Створення контакту скасовано.\n\n"
        "Оберіть наступну дію:",
        reply_markup=dashboard_keyboard.account_contact_menu
    )

    # Clear the state
    await state.clear()
