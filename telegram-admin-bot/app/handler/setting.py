from aiogram import F, Router
from aiogram.fsm.context import FSMContext
from aiogram.fsm.state import StatesGroup, State
from aiogram.types import Message, CallbackQuery

from app.keyboard.settings_keyboard import (
    settings_menu,
    back_to_settings,
    api_settings_menu,
    notification_settings_menu,
    admin_management_menu
)

# Create settings router
settings_router = Router()


class APISettings(StatesGroup):
    waiting_for_api_url = State()
    waiting_for_api_key = State()


@settings_router.callback_query(F.data == "settings")
async def settings_menu_handler(callback: CallbackQuery):
    text = (
        "⚙️ *Налаштування* \n\n"
        "Оберіть розділ налаштувань:"
    )

    await callback.message.edit_text(text, reply_markup=settings_menu, parse_mode="Markdown")
    await callback.answer()


@settings_router.callback_query(F.data == "api_settings")
async def api_settings_handler(callback: CallbackQuery):
    # Get current API settings
    # api_config = await get_api_config()

    # Mock data
    api_config = {
        "api_url": "http://localhost:8081/api/v1",
        "nova_poshta_key": "********"
    }

    text = (
        "🔑 *API Налаштування* \n\n"
        f"API URL: {api_config['api_url']}\n"
        f"Ключ Нової Пошти: {api_config['nova_poshta_key']}\n"
    )

    await callback.message.edit_text(text, reply_markup=api_settings_menu, parse_mode="Markdown")
    await callback.answer()


@settings_router.callback_query(F.data == "notification_settings")
async def notification_settings_handler(callback: CallbackQuery):
    text = (
        "🔔 *Налаштування сповіщень* \n\n"
        "Автоматичні сповіщення:\n"
        "✅ Підтвердження замовлення\n"
        "✅ Зміна статусу замовлення\n"
        "❌ Регулярні новини\n"
    )

    await callback.message.edit_text(text, reply_markup=notification_settings_menu, parse_mode="Markdown")
    await callback.answer()


@settings_router.callback_query(F.data == "admin_management")
async def admin_management_handler(callback: CallbackQuery):
    # Get current admins
    # admins = await get_admins()

    # Mock data
    admins = [
        {"id": 123456789, "name": "Адміністратор 1"},
        {"id": 987654321, "name": "Адміністратор 2"}
    ]

    text = "👤 *Адміністратори* \n\n"

    for admin in admins:
        text += f"• {admin['name']} (ID: {admin['id']})\n"

    await callback.message.edit_text(text, reply_markup=admin_management_menu, parse_mode="Markdown")
    await callback.answer()


@settings_router.callback_query(F.data == "change_api_url")
async def change_api_url_start(callback: CallbackQuery, state: FSMContext):
    await state.set_state(APISettings.waiting_for_api_url)

    text = "🔄 Введіть новий API URL:"

    await callback.message.edit_text(text)
    await callback.answer()


@settings_router.message(APISettings.waiting_for_api_url)
async def process_api_url(message: Message, state: FSMContext):
    api_url = message.text

    if not api_url.startswith(("http://", "https://")):
        await message.answer("❌ URL повинен починатися з http:// або https://")
        return

    # Call API to update API URL
    # success = await update_api_url(api_url)
    success = True  # Mock response

    if success:
        text = f"✅ API URL успішно оновлено на {api_url}"
    else:
        text = "❌ Помилка при оновленні API URL"

    await message.answer(text, reply_markup=back_to_settings)
    await state.clear()


@settings_router.callback_query(F.data == "list_admins")
async def list_admins(callback: CallbackQuery):
    # Get current admins
    # admins = await get_admins()

    # Mock data
    admins = [
        {"id": 123456789, "name": "Адміністратор 1"},
        {"id": 987654321, "name": "Адміністратор 2"}
    ]

    text = "👤 *Список адміністраторів* \n\n"

    if admins:
        for admin in admins:
            text += f"• {admin['name']} (ID: {admin['id']})\n"
    else:
        text += "Адміністраторів не знайдено."

    await callback.message.edit_text(text, reply_markup=admin_management_menu, parse_mode="Markdown")
    await callback.answer()


@settings_router.callback_query(F.data == "add_admin")
async def add_admin_start(callback: CallbackQuery, state: FSMContext):
    text = "👤 Перешліть повідомлення від користувача, якого ви хочете додати до адміністраторів:"

    await callback.message.edit_text(text)
    await callback.answer()
