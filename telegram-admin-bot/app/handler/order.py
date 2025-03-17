from aiogram import F, Router
from aiogram.fsm.context import FSMContext
from aiogram.fsm.state import StatesGroup, State
from aiogram.types import Message, CallbackQuery, InlineKeyboardMarkup, InlineKeyboardButton

from app.keyboard.order_keyboard import (
    order_management_menu,
    back_to_order_management,
    order_status_menu,
    order_details_menu,
    new_order_details_menu
)

# Create orders router
order_router = Router()


class OrderStatus(StatesGroup):
    waiting_for_order_id = State()
    waiting_for_new_status = State()
    waiting_for_comment = State()


@order_router.callback_query(F.data == "order_management")
async def order_management(callback: CallbackQuery):
    text = (
        "📦 *Управління замовленнями* \n\n"
        "Оберіть дію:"
    )

    await callback.message.edit_text(text, reply_markup=order_management_menu, parse_mode="Markdown")
    await callback.answer()


@order_router.callback_query(F.data == "new_orders")
async def new_orders(callback: CallbackQuery):
    """Show only new orders"""
    # Call API to get new orders
    # orders = await get_orders_by_status("NEW")

    # Mock response
    orders = fetch_new_orders()

    text = "🆕 *Нові замовлення:* \n\n"

    if not orders:
        text += "Нових замовлень немає."
    else:
        for order in orders:
            text += (
                f"#{order['id']} - {order['user_name']}\n"
                f"   Дата: {order['date']}\n\n"
            )

    # Create keyboard with buttons for each order
    keyboard = []
    for order in orders:
        keyboard.append([
            InlineKeyboardButton(
                text=f"#{order['id']} - {order['user_name']}",
                callback_data=f"view_new_order_{order['id']}"
            )
        ])

    keyboard.append([InlineKeyboardButton(text="🔙 Назад", callback_data="order_management")])
    orders_keyboard = InlineKeyboardMarkup(inline_keyboard=keyboard)

    await callback.message.edit_text(text, reply_markup=orders_keyboard, parse_mode="Markdown")
    await callback.answer()


@order_router.callback_query(F.data.startswith("view_new_order_"))
async def view_new_order(callback: CallbackQuery):
    """View details of a new order with waybill options"""
    order_id = int(callback.data.split("_")[-1])

    # Call API to get order details
    # order = await get_order_by_id(order_id)

    # Mock response
    order = {
        "id": order_id,
        "user_name": "Іван Петренко",
        "user_id": 1,
        "status": "NEW",
        "date": "2023-05-01",
        "description": "iPhone 13 чохол, чорний",
        "address": "м. Київ, Відділення НП №12"
    }

    text = (
        f"📦 *Нове замовлення #{order_id}* \n\n"
        f"👤 Клієнт: {order['user_name']}\n"
        f"📅 Дата: {order['date']}\n"
        f"📝 Опис: {order['description']}\n"
        f"📍 Адреса: {order['address']}\n"
        f"🔄 Статус: {order['status']}\n\n"
        "Оберіть дію для цього замовлення:"
    )

    await callback.message.edit_text(text, reply_markup=new_order_details_menu(order_id), parse_mode="Markdown")
    await callback.answer()


@order_router.callback_query(F.data == "view_orders")
async def view_orders(callback: CallbackQuery):
    # Call API to get all orders
    # orders = await get_orders()

    # Mock response
    orders = [
        {"id": 12345, "user_name": "Іван Петренко", "status": "NEW", "date": "2023-05-01"},
        {"id": 12346, "user_name": "Марія Коваленко", "status": "PROCESSING", "date": "2023-05-02"},
        {"id": 12347, "user_name": "Олег Сидоренко", "status": "COMPLETED", "date": "2023-05-03"}
    ]

    text = "📦 *Всі замовлення:* \n\n"

    # Create keyboard with buttons for each order
    keyboard = []
    for order in orders:
        status_emoji = "🆕" if order["status"] == "NEW" else "⏳" if order["status"] == "PROCESSING" else "✅"
        keyboard.append([
            InlineKeyboardButton(
                text=f"{status_emoji} #{order['id']} - {order['user_name']} ({order['status']})",
                callback_data=f"view_order_{order['id']}"
            )
        ])

    keyboard.append([InlineKeyboardButton(text="🔙 Назад", callback_data="order_management")])
    orders_keyboard = InlineKeyboardMarkup(inline_keyboard=keyboard)

    await callback.message.edit_text(text, reply_markup=orders_keyboard, parse_mode="Markdown")
    await callback.answer()


@order_router.callback_query(F.data.startswith("view_order_"))
async def view_order(callback: CallbackQuery):
    order_id = int(callback.data.split("_")[-1])

    # Call API to get order details
    # order = await get_order_by_id(order_id)

    # Mock response
    order = {
        "id": order_id,
        "user_name": "Іван Петренко",
        "user_id": 1,
        "status": "PROCESSING",
        "date": "2023-05-01",
        "description": "iPhone 13 чохол, чорний",
        "tracking_number": order_id > 12345 and "59000123456789" or None,
        "address": "м. Київ, Відділення НП №12"
    }

    text = (
        f"📦 *Замовлення #{order_id}* \n\n"
        f"👤 Клієнт: {order['user_name']}\n"
        f"📅 Дата: {order['date']}\n"
        f"📝 Опис: {order['description']}\n"
        f"🚚 Трек-номер: {order.get('tracking_number', 'Не вказано')}\n"
        f"📍 Адреса: {order['address']}\n"
        f"🔄 Статус: {order['status']}\n"
    )

    await callback.message.edit_text(text, reply_markup=order_details_menu(order_id), parse_mode="Markdown")
    await callback.answer()


@order_router.callback_query(F.data == "search_order")
async def search_order_start(callback: CallbackQuery, state: FSMContext):
    await state.set_state(OrderStatus.waiting_for_order_id)

    text = "🔍 Введіть номер замовлення:"

    await callback.message.edit_text(text)
    await callback.answer()


@order_router.message(OrderStatus.waiting_for_order_id)
async def process_order_search(message: Message, state: FSMContext):
    try:
        order_id = int(message.text)
        await state.update_data(order_id=order_id)

        # Call API to get order details
        # order = await get_order_by_id(order_id)

        # Mock response
        order = {
            "id": order_id,
            "user_name": "Іван Петренко",
            "user_id": 1,
            "status": "PROCESSING",
            "date": "2023-05-01",
            "description": "iPhone 13 чохол, чорний",
            "tracking_number": "59000123456789",
            "address": "м. Київ, Відділення НП №12"
        }

        if not order:
            await message.answer("❌ Замовлення не знайдено.", reply_markup=back_to_order_management)
            await state.clear()
            return

        text = (
            f"📦 *Замовлення #{order_id}* \n\n"
            f"👤 Клієнт: {order['user_name']}\n"
            f"📅 Дата: {order['date']}\n"
            f"📝 Опис: {order['description']}\n"
            f"🚚 Трек-номер: {order.get('tracking_number', 'Не вказано')}\n"
            f"📍 Адреса: {order['address']}\n"
            f"🔄 Статус: {order['status']}\n"
        )

        await message.answer(text, reply_markup=order_details_menu(order_id), parse_mode="Markdown")
        await state.clear()

    except ValueError:
        await message.answer("❌ Невірний формат номера замовлення. Спробуйте ще раз.")


@order_router.callback_query(F.data.startswith("update_status_"))
async def update_status_start(callback: CallbackQuery, state: FSMContext):
    order_id = int(callback.data.split("_")[-1])
    await state.update_data(order_id=order_id)
    await state.set_state(OrderStatus.waiting_for_new_status)

    text = "🔄 Оберіть новий статус замовлення:"

    await callback.message.edit_text(text, reply_markup=order_status_menu)
    await callback.answer()


@order_router.callback_query(OrderStatus.waiting_for_new_status, F.data.startswith('status_'))
async def process_status_update(callback: CallbackQuery, state: FSMContext):
    new_status = callback.data.split('_')[-1]
    await state.update_data(new_status=new_status)

    # If the status is COMPLETED or CANCELLED, ask for a comment
    if new_status in ["COMPLETED", "CANCELLED"]:
        await state.set_state(OrderStatus.waiting_for_comment)
        text = "📝 Додайте коментар до зміни статусу:"
        await callback.message.edit_text(text)
    else:
        # Update order status directly
        data = await state.get_data()
        order_id = data["order_id"]

        # Call API to update order status
        # success = await update_order_status(order_id, new_status)
        success = True  # Mock response

        if success:
            text = f"✅ Статус замовлення #{order_id} оновлено на {new_status}."
        else:
            text = f"❌ Помилка при оновленні статусу замовлення #{order_id}."

        await callback.message.edit_text(text, reply_markup=back_to_order_management)
        await state.clear()

    await callback.answer()


@order_router.message(OrderStatus.waiting_for_comment)
async def process_status_comment(message: Message, state: FSMContext):
    comment = message.text
    data = await state.get_data()
    order_id = data["order_id"]
    new_status = data["new_status"]

    # Call API to update order status with comment
    # success = await update_order_status(order_id, new_status, comment)
    success = True  # Mock response

    if success:
        text = f"✅ Статус замовлення #{order_id} оновлено на {new_status}.\n📝 Коментар: {comment}"
    else:
        text = f"❌ Помилка при оновленні статусу замовлення #{order_id}."

    await message.answer(text, reply_markup=back_to_order_management)
    await state.clear()


@order_router.callback_query(F.data.startswith("accept_order_"))
async def accept_order(callback: CallbackQuery):
    """Accept new order and change status to PROCESSING"""
    order_id = int(callback.data.split("_")[-1])

    # Call API to update order status
    # success = await update_order_status(order_id, "PROCESSING")
    success = True  # Mock response

    if success:
        text = f"✅ Замовлення #{order_id} прийнято в обробку."
    else:
        text = f"❌ Помилка при оновленні статусу замовлення #{order_id}."

    await callback.message.edit_text(text, reply_markup=back_to_order_management)
    await callback.answer()


@order_router.callback_query(F.data.startswith("decline_order_"))
async def decline_order(callback: CallbackQuery, state: FSMContext):
    """Decline order with reason"""
    order_id = int(callback.data.split("_")[-1])
    await state.update_data(order_id=order_id)
    await state.set_state(OrderStatus.waiting_for_comment)

    text = "❓ Вкажіть причину відхилення замовлення:"

    await callback.message.edit_text(text)
    await callback.answer()


@order_router.message(F.text == "📦 Замовлення")
async def orders_from_reply(message: Message):
    """Handle orders management from reply keyboard"""
    text = (
        "📦 *Управління замовленнями* \n\n"
        "Оберіть дію:"
    )

    await message.answer(text, reply_markup=order_management_menu, parse_mode="Markdown")
