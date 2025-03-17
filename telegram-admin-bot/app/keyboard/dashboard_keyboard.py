from aiogram.types import InlineKeyboardMarkup, InlineKeyboardButton

# Main admin menu
admin_main_menu = InlineKeyboardMarkup(inline_keyboard=[
    [InlineKeyboardButton(text="📊 Дашборд", callback_data="admin_dashboard")],
    [InlineKeyboardButton(text="👥 Управління користувачами", callback_data="user_management")],
    [InlineKeyboardButton(text="📦 Управління замовленнями", callback_data="order_management")],
    [InlineKeyboardButton(text="👨‍💼 Управління відправниками", callback_data="sender_management")],
])

# Dashboard menu
dashboard_menu = InlineKeyboardMarkup(inline_keyboard=[
    [InlineKeyboardButton(text="📅 Статистика за день", callback_data="stats_day")],
    [InlineKeyboardButton(text="📅 Статистика за тиждень", callback_data="stats_week")],
    [InlineKeyboardButton(text="📅 Статистика за місяць", callback_data="stats_month")],
    [InlineKeyboardButton(text="🔙 Назад", callback_data="back_to_main")]
])

# Back to main menu - this can be used across all modules
back_to_main = InlineKeyboardMarkup(inline_keyboard=[
    [InlineKeyboardButton(text="🔙 Повернутися в головне меню", callback_data="back_to_main")]
])
