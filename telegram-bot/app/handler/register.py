import re
from aiogram import F, Router
from aiogram.fsm.context import FSMContext
from aiogram.fsm.state import StatesGroup, State
from aiogram.types import Message, CallbackQuery, InlineKeyboardMarkup, InlineKeyboardButton

from app.client.APIClient import create_user_contact, fetch_user, fetch_cities, fetch_warehouses
from app.keyboard.keyboard import confirm_nova_post_registration

register_router = Router()


class Register(StatesGroup):
    telegram_id = State()
    first_name = State()
    last_name = State()
    number = State()
    city = State()
    city_ref = State()
    nova_post_address = State()
    warehouse_ref = State()
    message_id = State()


@register_router.callback_query(F.data == 'register')
async def register(callback: CallbackQuery, state: FSMContext):
    user_id = callback.from_user.id

    await state.update_data(telegram_id=user_id)
    await state.set_state(Register.first_name)

    await callback.answer("Давайте зареєструємо вас!")

    await update_registration_message(callback.message, state)
    await callback.message.answer('Введіть ваше імʼя:')


@register_router.message(Register.first_name)
async def first_name(message: Message, state: FSMContext):
    await state.update_data(first_name=message.text)
    await state.set_state(Register.last_name)

    await update_registration_message(message, state)
    await message.answer('Введіть ваше прізвище:')


@register_router.message(Register.last_name)
async def last_name(message: Message, state: FSMContext):
    await state.update_data(last_name=message.text)
    await state.set_state(Register.city)

    await update_registration_message(message, state)
    await message.answer('Введіть місто:')


@register_router.message(Register.city)
async def process_city_input(message: Message, state: FSMContext):
    cities = await fetch_cities(message.text)

    if not cities or len(cities) == 0:
        await message.answer('❌ Місто не знайдено. Спробуйте ще раз або введіть частину назви міста:')
        return

    if len(cities) == 1:
        city_description = cities[0].description
        city_ref = cities[0].ref

        await state.update_data(city=city_description)
        await state.update_data(city_ref=city_ref)

        await state.set_state(Register.nova_post_address)
        await update_registration_message(message, state)
        await message.answer(f'Обрано місто: {city_description}\n\nВведіть номер або назву відділення Нової Пошти:')
    else:
        kb = InlineKeyboardMarkup(inline_keyboard=[
            [InlineKeyboardButton(
                text=option.description,
                callback_data=f"city_{option.ref}|{option.description}"
            )]
            for option in cities[:10]
        ])

        await message.answer('Оберіть місто зі списку:', reply_markup=kb)


@register_router.callback_query(lambda c: c.data.startswith('city_'))
async def process_city_selection(callback: CallbackQuery, state: FSMContext):
    parts = callback.data.split('|')

    if len(parts) != 2:
        await callback.message.answer('❌ Помилка при виборі міста. Спробуйте ще раз.')
        await callback.answer()
        return

    city_ref = parts[0].split('_')[1]
    city_description = parts[1]

    await state.update_data(city=city_description)
    await state.update_data(city_ref=city_ref)
    await state.set_state(Register.nova_post_address)

    await callback.message.delete()

    await callback.answer()
    await update_registration_message(callback.message, state)
    await callback.message.answer(
        f'Обрано місто: {city_description}\n\nВведіть номер або назву відділення Нової Пошти:')


@register_router.message(Register.nova_post_address)
async def nova_post_address(message: Message, state: FSMContext):
    data = await state.get_data()
    if not data.get('city_ref'):
        await message.answer('❌ Помилка: не вибрано місто. Почніть реєстрацію знову.')
        await state.clear()
        return

    warehouse_request = {
        "cityRef": data.get('city_ref'),
        "findByString": message.text
    }

    warehouses = await fetch_warehouses(warehouse_request)

    if not warehouses or len(warehouses) == 0:
        await message.answer('❌ Відділення не знайдено. Спробуйте ввести інший номер або назву:')
        return

    if len(warehouses) == 1:
        await state.update_data(nova_post_address=warehouses[0].description)
        await state.update_data(warehouse_ref=warehouses[0].ref)

        await message.answer(f"Ваше відділення: {warehouses[0].description}\n\nПідтвердити?",
                             reply_markup=confirm_nova_post_registration)
    else:
        kb = InlineKeyboardMarkup(inline_keyboard=[
            [InlineKeyboardButton(
                text=f"{i + 1}. {warehouse.description[:30]}",
                callback_data=f"warehouse_{warehouse.ref}"
            )]
            for i, warehouse in enumerate(warehouses[:5])
        ])

        await message.answer('Оберіть відділення зі списку:', reply_markup=kb)


@register_router.callback_query(lambda c: c.data.startswith('warehouse_'))
async def warehouse_callback(callback: CallbackQuery, state: FSMContext):
    data = await state.get_data()

    parts = callback.data.split('|')
    warehouse_ref = parts[0].split('_')[1]

    warehouse_request = {
        "cityRef": data.get('city_ref'),
        "ref": warehouse_ref
    }

    warehouses = await fetch_warehouses(warehouse_request)

    if not warehouses or len(warehouses) != 1:
        await callback.message.answer('❌ Помилка при виборі відділення. Спробуйте ще раз.')
        await callback.answer()
        return

    await state.update_data(nova_post_address=warehouses[0].description)
    await state.update_data(warehouse_ref=warehouses[0].ref)

    await callback.message.delete()
    await callback.message.answer(f"Ваше відділення: {warehouses[0].description}\n\nПідтвердити?",
                                  reply_markup=confirm_nova_post_registration)
    await callback.answer()


@register_router.callback_query(F.data == 'confirm_nova_post')
async def confirm_nova_post(callback: CallbackQuery, state: FSMContext):
    await callback.message.edit_reply_markup(reply_markup=None)

    await state.set_state(Register.number)

    await callback.answer()

    await update_registration_message(callback.message, state)
    await callback.message.answer('Введіть номер телефону:')


@register_router.callback_query(F.data == 'retry_nova_post')
async def retry_nova_post(callback: CallbackQuery, state: FSMContext):
    await callback.message.edit_reply_markup(reply_markup=None)
    await state.set_state(Register.nova_post_address)
    await callback.message.answer('Введіть номер або назву відділення Нової Пошти:')
    await callback.answer()


@register_router.message(Register.number)
async def register_finish(message: Message, state: FSMContext):
    # Валидация номера телефона
    phone_number = message.text.strip()
    phone_pattern = r'^\+?[0-9]{10,13}$'

    if not re.match(phone_pattern, phone_number):
        await message.answer('❌ Невірний формат номера телефону. Введіть номер у форматі +380XXXXXXXXX або 0XXXXXXXXX:')
        return

    await state.update_data(number=phone_number)
    data = await state.get_data()

    contact_data = {
        "contactCreateEditDto": {
            "firstName": data["first_name"],
            "lastName": data["last_name"],
            "phoneNumber": data["number"],
            "cityRef": data["city_ref"],
            "warehouseRef": data["warehouse_ref"],
        }
    }

    user = await fetch_user(data["telegram_id"])
    response = await create_user_contact(user.id, contact_data)

    if response.status_code == 201:
        await message.answer(
            f"✅ *Реєстрація завершена!*\n\n"
            f"👤 Імʼя: {data['first_name']}\n"
            f"👤 Призвище: {data['last_name']}\n"
            f"📍 Місто: {data['city']}\n"
            f"🏢 Відділення Нової Пошти: {data['nova_post_address']}\n"
            f"📞 Номер телефону: {data['number']}\n"
            "Дякуємо за реєстрацію!",
            parse_mode="Markdown"
        )
    else:
        await message.answer("❌ Виникла помилка при створенні користувача. Спробуйте ще раз.")

    await state.clear()


async def update_registration_message(message: Message, state: FSMContext):
    data = await state.get_data()

    text = (
        "📋 *Ваша інформація:* \n\n"
        f"👤 Ім'я: {data.get('first_name', '❌ Не вказано')}\n"
        f"👤 Призвище: {data.get('last_name', '❌ Не вказано')}\n"
        f"📍 Місто: {data.get('city', '❌ Не вказано')}\n"
        f"🏢 Відділення Нової Пошти: {data.get('nova_post_address', '❌ Не вказано')}\n"
        f"📞 Телефон: {data.get('number', '❌ Не вказано')}\n\n"
    )

    message_id = data.get("message_id")
    if message_id:
        await message.bot.edit_message_text(text, chat_id=message.chat.id, message_id=int(message_id),
                                            parse_mode="Markdown")
    else:
        new_message = await message.answer(text, parse_mode="Markdown")
        await state.update_data(message_id=new_message.message_id)
