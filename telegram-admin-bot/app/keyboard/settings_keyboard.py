from aiogram.types import InlineKeyboardMarkup, InlineKeyboardButton

# Settings menu
settings_menu = InlineKeyboardMarkup(inline_keyboard=[
    [InlineKeyboardButton(text="🔑 API налаштування", callback_data="api_settings")],
    [InlineKeyboardButton(text="🔔 Налаштування сповіщень", callback_data="notification_settings")],
    [InlineKeyboardButton(text="👤 Управління адміністраторами", callback_data="admin_management")],
    [InlineKeyboardButton(text="🔙 Назад", callback_data="back_to_main")]
])

# Back to settings
back_to_settings = InlineKeyboardMarkup(inline_keyboard=[
    [InlineKeyboardButton(text="🔙 Назад до налаштувань", callback_data="settings")]
])

# API settings menu
api_settings_menu = InlineKeyboardMarkup(inline_keyboard=[
    [InlineKeyboardButton(text="🔄 Змінити API URL", callback_data="change_api_url")],
    [InlineKeyboardButton(text="🔑 Змінити API ключ", callback_data="change_api_key")],
    [InlineKeyboardButton(text="🧪 Тест з'єднання", callback_data="test_api_connection")],
    [InlineKeyboardButton(text="🔙 Назад", callback_data="settings")]
])

# Notification settings menu
notification_settings_menu = InlineKeyboardMarkup(inline_keyboard=[
    [InlineKeyboardButton(text="🔔 Автоматичні сповіщення", callback_data="auto_notifications")],
    [InlineKeyboardButton(text="⏰ Налаштування розкладу", callback_data="schedule_settings")],
    [InlineKeyboardButton(text="💬 Налаштування шаблонів", callback_data="template_settings")],
    [InlineKeyboardButton(text="🔙 Назад", callback_data="settings")]
])

# Admin management menu
admin_management_menu = InlineKeyboardMarkup(inline_keyboard=[
    [InlineKeyboardButton(text="➕ Додати адміністратора", callback_data="add_admin")],
    [InlineKeyboardButton(text="👤 Список адміністраторів", callback_data="list_admins")],
    [InlineKeyboardButton(text="🔙 Назад", callback_data="settings")]
])

# Confirm settings change
confirm_settings_change = InlineKeyboardMarkup(inline_keyboard=[
    [InlineKeyboardButton(text="✅ Підтвердити", callback_data="confirm_settings"),
     InlineKeyboardButton(text="❌ Скасувати", callback_data="cancel_settings")],
])


# Toggle notification setting
def toggle_notification_menu(notification_type, is_enabled):
    """Create keyboard for toggling notification setting"""
    button_text = f"{'🔕 Вимкнути' if is_enabled else '🔔 Увімкнути'} {notification_type}"
    callback_data = f"toggle_{notification_type.lower().replace(' ', '_')}_{0 if is_enabled else 1}"

    return InlineKeyboardMarkup(inline_keyboard=[
        [InlineKeyboardButton(text=button_text, callback_data=callback_data)],
        [InlineKeyboardButton(text="🔙 Назад", callback_data="notification_settings")]
    ])


# Admin actions menu
def admin_actions_menu(admin_id):
    """Create keyboard for admin actions"""
    return InlineKeyboardMarkup(inline_keyboard=[
        [InlineKeyboardButton(text="🚫 Видалити з адміністраторів", callback_data=f"remove_admin_{admin_id}")],
        [InlineKeyboardButton(text="🔙 Назад", callback_data="list_admins")]
    ])
