from aiogram.types import InlineKeyboardMarkup, InlineKeyboardButton
from aiogram.utils.keyboard import InlineKeyboardBuilder

# Main menu keyboard
main_menu = InlineKeyboardMarkup(inline_keyboard=[
    [InlineKeyboardButton(text="📇 Управління контактами аккаунта", callback_data="manage_account_contacts")],
    [InlineKeyboardButton(text="📦 Управління замовленнями", callback_data="manage_orders")],
    [InlineKeyboardButton(text="👥 Управління користувачами", callback_data="manage_users")]
])

# Account contact management menu
account_contact_menu = InlineKeyboardMarkup(inline_keyboard=[
    [InlineKeyboardButton(text="➕ Додати контакт аккаунта", callback_data="add_account_contact")],
    [InlineKeyboardButton(text="📋 Список контактів аккаунта", callback_data="list_account_contacts")],
    [InlineKeyboardButton(text="🔙 Назад до головного меню", callback_data="back_to_main")]
])

# Order management menu
order_menu = InlineKeyboardMarkup(inline_keyboard=[
    [InlineKeyboardButton(text="📋 Список замовлень", callback_data="list_orders")],
    [InlineKeyboardButton(text="🔍 Знайти замовлення за ID", callback_data="find_order")],
    [InlineKeyboardButton(text="🔙 Назад до головного меню", callback_data="back_to_main")]
])

# User management menu
user_menu = InlineKeyboardMarkup(inline_keyboard=[
    [InlineKeyboardButton(text="➕ Створити користувача", callback_data="create_user")],
    [InlineKeyboardButton(text="👤 Список користувачів", callback_data="list_users")],
    [InlineKeyboardButton(text="🔙 Назад до головного меню", callback_data="back_to_main")]
])

# Back buttons
back_to_account_contacts = InlineKeyboardMarkup(inline_keyboard=[
    [InlineKeyboardButton(text="🔙 Назад до контактів аккаунта", callback_data="manage_account_contacts")]
])

back_to_orders = InlineKeyboardMarkup(inline_keyboard=[
    [InlineKeyboardButton(text="🔙 Назад до замовлень", callback_data="manage_orders")]
])

back_to_users = InlineKeyboardMarkup(inline_keyboard=[
    [InlineKeyboardButton(text="🔙 Назад до користувачів", callback_data="manage_users")]
])

# Confirmation buttons
confirm_buttons = InlineKeyboardMarkup(inline_keyboard=[
    [
        InlineKeyboardButton(text="✅ Підтвердити", callback_data="confirm_action"),
        InlineKeyboardButton(text="❌ Скасувати", callback_data="cancel_action")
    ]
])


# Pagination helper function
def create_pagination_keyboard(current_page, total_pages, base_callback):
    keyboard = InlineKeyboardBuilder()

    # Previous page button
    if current_page > 0:
        keyboard.add(InlineKeyboardButton(
            text="⬅️ Назад",
            callback_data=f"{base_callback}_page_{current_page - 1}"
        ))

    # Page indicator
    keyboard.add(InlineKeyboardButton(
        text=f"Сторінка {current_page + 1}/{total_pages}",
        callback_data="current_page"
    ))

    # Next page button
    if current_page < total_pages - 1:
        keyboard.add(InlineKeyboardButton(
            text="➡️ Вперед",
            callback_data=f"{base_callback}_page_{current_page + 1}"
        ))

    # Back button
    keyboard.row()
    keyboard.add(InlineKeyboardButton(
        text="🔙 Назад",
        callback_data=f"back_to_{base_callback.split('_')[0]}"
    ))

    return keyboard.adjust(3, 1).as_markup()
