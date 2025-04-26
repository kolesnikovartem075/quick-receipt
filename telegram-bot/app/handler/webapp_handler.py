import json
from aiogram import Router, F
from aiogram.filters import CommandStart
from aiogram.fsm.context import FSMContext
from aiogram.types import Message, WebAppInfo, InlineKeyboardMarkup, InlineKeyboardButton

from app.client.APIClient import create_user, create_user_contact, fetch_user, fetch_user_contact
from app.handler.order import show_user_details

webapp_router = Router()

WEBAPP_URL = "https://localhost:8000"


def get_webapp_keyboard():
    web_app = WebAppInfo(url=WEBAPP_URL)

    keyboard = InlineKeyboardMarkup(inline_keyboard=[
        [InlineKeyboardButton(text="📝 Зареєструватись", web_app=web_app)]
    ])

    return keyboard


# @webapp_router.message(CommandStart())
async def start_webapp(message: Message, state: FSMContext):
    user = await fetch_user(message.from_user.id)

    if not user:
        await create_user({"externalUserId": message.from_user.id})

    await message.reply(f'Привіт {message.from_user.first_name},\n'
                        f'Для оформлення доставки треба зареєструватись.\n'
                        f'Натисніть на кнопку нижче, щоб відкрити форму реєстрації:',
                        reply_markup=get_webapp_keyboard())


@webapp_router.message(F.web_app_data)
async def web_app_data(message: Message, state: FSMContext):
    data = json.loads(message.web_app_data.data)

    if data.get('action') == 'register':
        user_data = data.get('data', {})
        telegram_id = message.from_user.id

        user = await fetch_user(telegram_id)
        if not user:
            user = await create_user({"externalUserId": telegram_id})

        response = await create_user_contact(user.id, user_data)

        if response and response.status_code == 201:

            contact_data = user_data.get('contactCreateEditDto', {})

            await message.answer(
                f"✅ *Реєстрація завершена!*\n\n"
                f"👤 Імʼя: {contact_data.get('firstName', '-')}\n"
                f"👤 Призвище: {contact_data.get('lastName', '-')}\n"
                f"📍 Місто: {contact_data.get('city', '-')}\n"
                f"🏢 Відділення Нової Пошти: {contact_data.get('postOffice', '-')}\n"
                f"📞 Номер телефону: {contact_data.get('phoneNumber', '-')}\n"
                "Дякуємо за реєстрацію!",
                parse_mode="Markdown"
            )

            user_contact = await fetch_user_contact(user.id)
            if user_contact:
                await show_user_details(message, user_contact, state)
        else:
            await message.answer("❌ Виникла помилка при створенні контакту. Спробуйте ще раз.")

    else:

        await message.answer(f"Отримано дані: {data}")
