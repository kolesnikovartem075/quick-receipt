from aiogram import Router, F
from aiogram.filters import CommandStart
from aiogram.fsm.context import FSMContext
from aiogram.fsm.state import StatesGroup, State
from aiogram.types import Message, CallbackQuery

from app.client.APIClient import fetch_account, fetch_user
from app.keyboard import dashboard_keyboard

# Create admin router
dashboard_router = Router()


class AdminStates(StatesGroup):
    """States for admin actions"""
    start = State()
    account_contact_entry = State()


# User identification and main menu
@dashboard_router.message(CommandStart())
async def admin_start(message: Message, state: FSMContext):
    """Admin bot entry point - identify user and show main menu"""
    admin_user = await fetch_user(message.from_user.id)
    account_data = await fetch_account(admin_user.account_id)

    if not admin_user or not account_data:
        await message.answer("❌ Помилка: Не вдалося отримати дані користувача або аккаунту.")
        return

    await state.update_data(
        user_id=admin_user.id,
        account_id=account_data.id,
    )

    # Welcome admin with main menu
    await message.answer(
        f"👋 Ласкаво просимо до Адмін-бота, {message.from_user.first_name}!\n\n"
        f"📊 Аккаунт: {account_data.name} ({account_data.nickname})\n"
        f"🔑 Ваша роль: {admin_user.role}\n"
        f"Будь ласка, оберіть опцію з меню нижче:",
        reply_markup=dashboard_keyboard.main_menu
    )

    await state.set_state(AdminStates.start)


# Main menu handlers - just the basic navigation
@dashboard_router.callback_query(F.data == "manage_account_contacts")
async def manage_account_contacts(callback: CallbackQuery, state: FSMContext):
    """Entry point for account contact management"""
    await callback.answer()

    await callback.message.edit_text(
        f"📇 Управління контактами аккаунта\n\n"
        f"Звідси ви можете додавати нові контакти з API-ключем або редагувати існуючі.\n\n"
        f"Будь ласка, оберіть опцію:",
        reply_markup=dashboard_keyboard.account_contact_menu
    )


@dashboard_router.callback_query(F.data == "manage_orders")
async def manage_orders(callback: CallbackQuery):
    """Entry point for order management"""
    await callback.answer()

    await callback.message.edit_text(
        "📦 Управління замовленнями\n\n"
        "Тут ви можете переглядати та керувати замовленнями, приймати запити та створювати накладні.\n\n"
        "Будь ласка, оберіть опцію:",
        reply_markup=dashboard_keyboard.order_menu
    )


@dashboard_router.callback_query(F.data == "manage_users")
async def manage_users(callback: CallbackQuery):
    """Entry point for user management"""
    await callback.answer()

    await callback.message.edit_text(
        "👥 Управління користувачами\n\n"
        "Тут ви можете створювати нових користувачів та керувати існуючими контактами користувачів.\n\n"
        "Будь ласка, оберіть опцію:",
        reply_markup=dashboard_keyboard.user_menu
    )


@dashboard_router.callback_query(F.data == "back_to_main")
async def back_to_main_menu(callback: CallbackQuery, state: FSMContext):
    """Return to main menu"""
    await callback.answer()

    # Get stored user and account info
    data = await state.get_data()

    await callback.message.edit_text(
        f"📊 Панель адміністратора\n\n"
        f"Аккаунт: {data.get('account_name')} ({data.get('account_nickname')})\n"
        f"Будь ласка, оберіть опцію з меню нижче:",
        reply_markup=dashboard_keyboard.main_menu
    )

    await state.set_state(AdminStates.start)


# Entry point for account contact creation - just a placeholder
@dashboard_router.callback_query(F.data == "add_account_contact")
async def add_account_contact(callback: CallbackQuery, state: FSMContext):
    """Entry point for creating a new account contact with API key"""
    await callback.answer()

    await callback.message.edit_text(
        "➕ Додати новий контакт аккаунта\n\n"
        "Давайте створимо новий контакт аккаунта з API-ключем.\n"
        "Цей функціонал буде реалізовано на наступному кроці.",
        reply_markup=dashboard_keyboard.back_to_account_contacts
    )

    # Set state for account contact creation
    await state.set_state(AdminStates.account_contact_entry)
