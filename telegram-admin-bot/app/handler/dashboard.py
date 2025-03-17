from aiogram import F, Router
from aiogram.filters import CommandStart
from aiogram.fsm.context import FSMContext
from aiogram.types import Message, CallbackQuery

from app.dto.Admin import Admin
from app.keyboard.dashboard_keyboard import admin_main_menu, dashboard_menu

# Create dashboard router
dashboard_router = Router()


@dashboard_router.message(CommandStart())
async def admin_start(message: Message, state: FSMContext):
    """Handle admin start command"""
    await message.reply(f'Вітаємо в адмін-панелі, {message.from_user.first_name}!',
                        reply_markup=admin_main_menu)


@dashboard_router.callback_query(F.data == "admin_dashboard")
async def admin_dashboard(callback: CallbackQuery):
    """Show admin dashboard with statistics"""
    stats = {
        'total_users': 120,
        'total_orders': 45,
        'pending_orders': 12,
        'completed_orders': 30,
        'new_today': 5
    }

    text = (
        "📊 *Панель адміністратора* \n\n"
        f"👥 Всього користувачів: {stats['total_users']}\n"
        f"📦 Всього замовлень: {stats['total_orders']}\n"
        f"⏳ Очікують обробки: {stats['pending_orders']}\n"
        f"✅ Виконано: {stats['completed_orders']}\n"
        f"🆕 Нових сьогодні: {stats['new_today']}\n"
    )

    await callback.message.edit_text(text, reply_markup=dashboard_menu, parse_mode="Markdown")
    await callback.answer()


@dashboard_router.callback_query(F.data == "stats_day")
async def stats_day(callback: CallbackQuery):
    """Show daily statistics"""
    # Fetch daily statistics
    # daily_stats = await get_daily_stats()

    # Mock data for development
    daily_stats = {
        'orders': 15,
        'revenue': 12500,
        'new_users': 8,
    }

    text = (
        "📅 *Статистика за сьогодні* \n\n"
        f"📦 Нових замовлень: {daily_stats['orders']}\n"
        f"💰 Дохід: {daily_stats['revenue']} грн\n"
        f"👤 Нових користувачів: {daily_stats['new_users']}\n"
    )

    await callback.message.edit_text(text, reply_markup=dashboard_menu, parse_mode="Markdown")
    await callback.answer()


@dashboard_router.callback_query(F.data == "back_to_main")
async def back_to_main_menu(callback: CallbackQuery):
    """Return to main admin menu"""
    text = "Головне меню адміністратора:"

    await callback.message.edit_text(text, reply_markup=admin_main_menu)
    await callback.answer()
