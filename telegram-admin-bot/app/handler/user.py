from aiogram import F, Router
from aiogram.fsm.context import FSMContext
from aiogram.fsm.state import StatesGroup, State
from aiogram.types import Message, CallbackQuery

from app.client.APIClient import get_user_by_id, get_users
from app.keyboard.user_keyboard import user_management_menu, back_to_user_management, \
    user_details_menu, create_user_selection_menu

# Create users router
user_router = Router()


class UserSearch(StatesGroup):
    waiting_for_query = State()


@user_router.callback_query(F.data == "user_management")
async def user_management(callback: CallbackQuery):
    """Show user management menu"""
    text = (
        "👥 *Управління користувачами* \n\n"
        "Оберіть дію:"
    )

    await callback.message.edit_text(text, reply_markup=user_management_menu, parse_mode="Markdown")
    await callback.answer()


@user_router.callback_query(F.data == "search_user")
async def search_user_start(callback: CallbackQuery, state: FSMContext):
    await state.set_state(UserSearch.waiting_for_query)

    text = "🔍 Введіть ім'я, прізвище, телефон або Telegram ID користувача:"

    await callback.message.edit_text(text)
    await callback.answer()


@user_router.message(UserSearch.waiting_for_query)
async def process_user_search(message: Message, state: FSMContext):
    query = message.text

    # Call API to search users
    # users = await search_users(query)

    # Mock response
    users = [
        {"id": 1, "first_name": "Іван", "last_name": "Петренко", "phone_number": "+380991234567"},
        {"id": 2, "first_name": "Марія", "last_name": "Коваленко", "phone_number": "+380671234567"}
    ]

    if not users:
        await message.answer("❌ Користувачів не знайдено.", reply_markup=back_to_user_management)
        await state.clear()
        return

    text = "🔍 *Результати пошуку:* \n\n"

    for i, user in enumerate(users, 1):
        text += (
            f"{i}. {user['first_name']} {user['last_name']}\n"
            f"   📞 {user['phone_number']}\n"
            f"   🆔 {user['id']}\n\n"
        )

    text += "Оберіть користувача або поверніться назад:"

    # Save users to state for future selection
    await state.update_data(search_results=users)

    await message.answer(text, reply_markup=create_user_selection_menu(users), parse_mode="Markdown")
    await state.clear()


@user_router.callback_query(F.data.startswith('view_user_'))
async def view_user(callback: CallbackQuery):
    user_id = int(callback.data.split('_')[-1])

    # Call API to get user details
    # user = await get_user_by_id(user_id)

    # Mock response
    user = {
        "id": user_id,
        "first_name": "Іван",
        "last_name": "Петренко",
        "phone_number": "+380991234567",
        "telegram_id": "123456789",
        "city": "Київ",
        "post_office": "Відділення №12",
        "orders_count": 5,
        "registration_date": "2023-01-15"
    }

    text = (
        f"👤 *Інформація про користувача* \n\n"
        f"Ім'я: {user['first_name']}\n"
        f"Прізвище: {user['last_name']}\n"
        f"📞 Телефон: {user['phone_number']}\n"
        f"🆔 Telegram ID: {user['telegram_id']}\n"
        f"🏙️ Місто: {user['city']}\n"
        f"🏢 Відділення НП: {user['post_office']}\n"
        f"📦 Кількість замовлень: {user['orders_count']}\n"
        f"📅 Дата реєстрації: {user['registration_date']}\n"
    )

    await callback.message.edit_text(text, reply_markup=user_details_menu(user_id), parse_mode="Markdown")
    await callback.answer()


@user_router.callback_query(F.data == "active_users")
async def active_users(callback: CallbackQuery):
    """Show list of active users"""

    # Mock active users for development
    active_users = [
        {"id": 1, "name": "Іван Петренко", "orders": 12},
        {"id": 2, "name": "Марія Коваленко", "orders": 8},
        {"id": 3, "name": "Олег Сидоренко", "orders": 5},
    ]

    text = "👥 *Активні користувачі* \n\n"

    for user in active_users:
        text += f"• {user['name']} - {user['orders']} замовлень\n"

    await callback.message.edit_text(text, reply_markup=back_to_user_management, parse_mode="Markdown")
    await callback.answer()
