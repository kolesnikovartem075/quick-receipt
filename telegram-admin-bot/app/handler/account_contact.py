from aiogram import Router, F
from aiogram.fsm.context import FSMContext
from aiogram.types import CallbackQuery

from app.keyboard import dashboard_keyboard

account_contact_router = Router()


@account_contact_router.callback_query(F.data == "confirm_action")
async def confirm_creation(callback: CallbackQuery, state: FSMContext):
    """Handle confirmation of account contact creation"""
    await callback.answer("Створюємо контакт...")

    # Get all collected data
    data = await state.get_data()

    # Get account_id from state
    account_id = data.get("account_id")

    if not account_id:
        await callback.message.edit_text(
            "❌ Помилка: Інформація про аккаунт відсутня.\n"
            "Спробуйте перезапустити бота командою /start",
            reply_markup=dashboard_keyboard.back_to_main
        )
        return

    # Prepare account contact data
    account_contact_data = {
        "api_key": data.get('api_key'),
        "contact_create_edit_dto": {
            "first_name": data.get('first_name'),
            "last_name": data.get('last_name'),
            "phone_number": data.get('phone_number'),
            "city_ref": data.get('city_ref'),
            "warehouse_ref": data.get('warehouse_ref')
        }
    }

    try:
        response = await create_account_contact(account_contact_data, account_id)

        # Success message
        await callback.message.edit_text(
            "✅ Контакт аккаунта успішно створено!\n\n"
            f"👤 Ім'я: {data.get('first_name')}\n"
            f"👤 Прізвище: {data.get('last_name')}\n"
            f"📍 Місто: {data.get('city')}\n"
            f"🏢 Відділення: {data.get('nova_post_address')}\n"
            f"📞 Телефон: {data.get('phone_number')}\n"
            f"🔑 API Ключ: {data.get('api_key')}\n\n"
            "Оберіть наступну дію:",
            reply_markup=dashboard_keyboard.account_contact_menu
        )

    except Exception as e:
        # Error message
        await callback.message.edit_text(
            f"❌ Помилка при створенні контакту: {str(e)}\n\n"
            "Спробуйте ще раз або поверніться до меню:",
            reply_markup=dashboard_keyboard.back_to_account_contacts
        )

    # Clear state
    await state.clear()
