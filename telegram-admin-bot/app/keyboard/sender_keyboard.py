from aiogram.types import InlineKeyboardMarkup, InlineKeyboardButton

# Sender management menu
sender_management_menu = InlineKeyboardMarkup(inline_keyboard=[
    [InlineKeyboardButton(text="➕ Створити відправника", callback_data="create_sender")],
    [InlineKeyboardButton(text="📋 Список відправників", callback_data="list_senders")],
    [InlineKeyboardButton(text="🔙 Назад", callback_data="back_to_main")]
])

# Back to sender management
back_to_sender_management = InlineKeyboardMarkup(inline_keyboard=[
    [InlineKeyboardButton(text="🔙 Назад до управління відправниками", callback_data="sender_management")]
])

# Cancel sender creation
cancel_sender_creation = InlineKeyboardMarkup(inline_keyboard=[
    [InlineKeyboardButton(text="❌ Скасувати створення", callback_data="cancel_sender_creation")]
])


def sender_details_menu(sender_id):
    """Create keyboard for sender details view"""
    return InlineKeyboardMarkup(inline_keyboard=[
        [InlineKeyboardButton(text="📝 Редагувати дані", callback_data=f"edit_sender_{sender_id}")],
        [InlineKeyboardButton(text="🗑️ Видалити відправника", callback_data=f"delete_sender_{sender_id}")],
        [InlineKeyboardButton(text="🔙 Назад", callback_data="list_senders")]
    ])


def create_sender_selection_menu(senders):
    """Create a keyboard with buttons for each sender"""
    keyboard = []

    for sender in senders:
        keyboard.append([
            InlineKeyboardButton(
                text=f"{sender['name']} ({sender['phone']})",
                callback_data=f"view_sender_{sender['id']}"
            )
        ])

    keyboard.append([InlineKeyboardButton(text="🔙 Назад", callback_data="sender_management")])

    return InlineKeyboardMarkup(inline_keyboard=keyboard)


def confirm_delete_sender(sender_id):
    """Create confirmation keyboard for sender deletion"""
    return InlineKeyboardMarkup(inline_keyboard=[
        [
            InlineKeyboardButton(text="✅ Так, видалити", callback_data=f"confirm_delete_sender_{sender_id}"),
            InlineKeyboardButton(text="❌ Ні, скасувати", callback_data=f"view_sender_{sender_id}")
        ]
    ])
