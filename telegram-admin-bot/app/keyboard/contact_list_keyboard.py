from aiogram.types import InlineKeyboardMarkup, InlineKeyboardButton

empty_contacts_keyboard = InlineKeyboardMarkup(inline_keyboard=[
    [InlineKeyboardButton(text="➕ Додати контакт", callback_data="add_account_contact")],
    [InlineKeyboardButton(text="⬅️ Назад", callback_data="manage_account_contacts")]
])

contacts_keyboard = InlineKeyboardMarkup(inline_keyboard=[
    [InlineKeyboardButton(text="➕ Додати контакт", callback_data="add_account_contact")],
    [InlineKeyboardButton(text="⬅️ Назад", callback_data="manage_account_contacts")]
])

error_keyboard = InlineKeyboardMarkup(inline_keyboard=[
    [InlineKeyboardButton(text="🔄 Спробувати ще", callback_data="list_account_contacts")],
    [InlineKeyboardButton(text="⬅️ Назад", callback_data="manage_account_contacts")]
])
