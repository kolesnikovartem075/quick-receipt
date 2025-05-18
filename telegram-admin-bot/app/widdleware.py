from functools import wraps
from typing import Callable

from aiogram.filters import Filter
from aiogram.fsm.context import FSMContext
from aiogram.types import CallbackQuery, InlineKeyboardMarkup, InlineKeyboardButton


def require_account_id(handler: Callable) -> Callable:
    """Decorator to check if account_id exists in state"""

    @wraps(handler)
    async def wrapper(callback: CallbackQuery, state: FSMContext, *args, **kwargs):
        data = await state.get_data()
        account_id = data.get("account_id")

        if not account_id:
            await callback.answer()
            await callback.message.edit_text(
                "❌ Помилка: Не вдалося отримати ID аккаунта.\n"
                "Спробуйте повернутися до головного меню та почати знову.",
                reply_markup=InlineKeyboardMarkup(inline_keyboard=[
                    [InlineKeyboardButton(text="⬅️ Назад", callback_data="manage_account_contacts")]
                ])
            )
            return None

        return await handler(callback, state, *args, **kwargs)

    return wrapper


class AccountIdFilter(Filter):
    """Filter that checks if account_id exists in state"""

    async def __call__(self, callback: CallbackQuery, state: FSMContext) -> bool:
        data = await state.get_data()
        return bool(data.get("account_id"))
