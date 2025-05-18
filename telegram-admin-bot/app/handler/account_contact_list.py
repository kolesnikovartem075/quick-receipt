from typing import List

from aiogram import Router, F
from aiogram.fsm.context import FSMContext
from aiogram.types import CallbackQuery

from app.client.APIClient import fetch_account_contacts
from app.keyboard.contact_list_keyboard import empty_contacts_keyboard, contacts_keyboard, error_keyboard
from app.models import AccountContactRead
from app.widdleware import require_account_id

account_contacts_router = Router()


@account_contacts_router.callback_query(F.data == "list_account_contacts")
@require_account_id
async def list_account_contacts(callback: CallbackQuery, state: FSMContext):
    """Show list of account contacts"""
    await callback.answer()

    data = await state.get_data()
    account_id = data.get("account_id")

    try:
        contacts = await fetch_account_contacts(account_id)
        if not contacts or len(contacts) == 0:
            await show_empty_contacts_message(callback)
            return

        await show_contacts_list(callback, contacts)
    except Exception as e:
        await show_error_message(callback, str(e))


async def show_empty_contacts_message(callback):
    """Show message when no contacts found"""
    await callback.message.edit_text(
        "📋 Контакти аккаунта\n\n"
        "У цього аккаунта поки немає контактів.\n\n"
        "Ви можете додати новий контакт за допомогою кнопки нижче.",
        reply_markup=empty_contacts_keyboard
    )


async def show_contacts_list(callback, contacts):
    """Show list of contacts"""
    contacts_text = format_contacts_message(contacts)

    await callback.message.edit_text(
        contacts_text,
        reply_markup=contacts_keyboard,
        parse_mode="Markdown"
    )


def format_contacts_message(contacts: List[AccountContactRead]):
    """Format message with contacts list"""
    message = "📋 *Контакти аккаунта:*\n\n"

    for i, account_contact in enumerate(contacts, 1):
        contact = account_contact.contact
        message += (
            f"{i}. *{contact.first_name} {contact.last_name}*\n"
            f"   📞 Телефон: {contact.phone_number}\n"
            f"   📍 Нова Пошта: {contact.warehouse.description}\n"
            f"   🆔 ID: {contact.id}\n\n"
        )

    return message


async def show_error_message(callback, error_text):
    """Show error message"""
    await callback.message.edit_text(
        f"❌ Помилка при отриманні контактів: {error_text}\n\n"
        "Спробуйте ще раз або поверніться до меню:",
        reply_markup=error_keyboard
    )
