from aiogram.types import InlineKeyboardMarkup, InlineKeyboardButton

# Simplified user management menu with only two options
user_management_menu = InlineKeyboardMarkup(inline_keyboard=[
    [InlineKeyboardButton(text="🔍 Пошук користувача", callback_data="search_user")],
    [InlineKeyboardButton(text="👥 Список користувачів", callback_data="list_users")],
    [InlineKeyboardButton(text="🔙 Назад", callback_data="back_to_main")]
])

# Back to user management
back_to_user_management = InlineKeyboardMarkup(inline_keyboard=[
    [InlineKeyboardButton(text="🔙 Назад до управління користувачами", callback_data="user_management")]
])


# Dynamic keyboards
def create_user_selection_menu(users):
    """Create a keyboard with buttons for each user"""
    keyboard = []

    for user in users:
        keyboard.append([
            InlineKeyboardButton(
                text=f"{user['first_name']} {user['last_name']}",
                callback_data=f"view_user_{user['id']}"
            )
        ])

    keyboard.append([InlineKeyboardButton(text="🔙 Назад", callback_data="user_management")])

    return InlineKeyboardMarkup(inline_keyboard=keyboard)


def user_details_menu(user_id):
    """Create a keyboard for user details view"""
    return InlineKeyboardMarkup(inline_keyboard=[
        [InlineKeyboardButton(text="📦 Переглянути замовлення", callback_data=f"user_orders_{user_id}")],
        [InlineKeyboardButton(text="🔙 Назад", callback_data="user_management")]
    ])