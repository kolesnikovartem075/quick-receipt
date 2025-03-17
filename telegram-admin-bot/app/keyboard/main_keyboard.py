from aiogram.types import ReplyKeyboardMarkup, KeyboardButton

# Main admin reply keyboard that will be shown at the bottom of the chat
admin_main_reply_keyboard = ReplyKeyboardMarkup(
    keyboard=[
        [KeyboardButton(text="📊 Головне меню")],
        [
            KeyboardButton(text="👥 Користувачі"),
            KeyboardButton(text="📦 Замовлення"),
            KeyboardButton(text="👨‍💼 Відправники")
        ],
        [KeyboardButton(text="❌ Скасувати")]
    ],
    resize_keyboard=True,
    is_persistent=True
)


# Create a function to add this keyboard to any message
def add_main_keyboard(message_text):
    """Helper function to add standard instructions about the main keyboard"""
    return (
        f"{message_text}\n\n"
        "Використовуйте головне меню внизу екрана для швидкої навігації або скасування дії."
    )
