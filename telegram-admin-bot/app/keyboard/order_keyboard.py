from aiogram.types import InlineKeyboardMarkup, InlineKeyboardButton

# Order management menu with new orders as the top option
order_management_menu = InlineKeyboardMarkup(inline_keyboard=[
    [InlineKeyboardButton(text="🆕 Нові замовлення", callback_data="new_orders")],
    [InlineKeyboardButton(text="📋 Всі замовлення", callback_data="view_orders")],
    [InlineKeyboardButton(text="🔍 Пошук замовлення", callback_data="search_order")],
    [InlineKeyboardButton(text="🔙 Назад", callback_data="back_to_main")]
])

# Back to order management
back_to_order_management = InlineKeyboardMarkup(inline_keyboard=[
    [InlineKeyboardButton(text="🔙 Назад до управління замовленнями", callback_data="order_management")]
])

# Order status menu
order_status_menu = InlineKeyboardMarkup(inline_keyboard=[
    [InlineKeyboardButton(text="⏳ В обробці", callback_data="status_PROCESSING")],
    [InlineKeyboardButton(text="🚚 Відправлено", callback_data="status_SHIPPED")],
    [InlineKeyboardButton(text="✅ Виконано", callback_data="status_COMPLETED")],
    [InlineKeyboardButton(text="❌ Скасовано", callback_data="status_CANCELLED")],
    [InlineKeyboardButton(text="🔙 Назад", callback_data="order_management")]
])


def order_details_menu(order_id):
    """Create keyboard for order details with waybill options"""
    return InlineKeyboardMarkup(inline_keyboard=[
        [InlineKeyboardButton(text="🚚 Створити накладну", callback_data=f"create_waybill_{order_id}")],
        [InlineKeyboardButton(text="❌ Відхилити замовлення", callback_data=f"decline_order_{order_id}")],
        [InlineKeyboardButton(text="🔄 Змінити статус", callback_data=f"update_status_{order_id}")],
        [InlineKeyboardButton(text="🔙 Назад", callback_data="order_management")]
    ])


def new_order_details_menu(order_id):
    """Create special keyboard for new orders with waybill focus"""
    return InlineKeyboardMarkup(inline_keyboard=[
        [InlineKeyboardButton(text="✅ Прийняти замовлення", callback_data=f"accept_order_{order_id}")],
        [InlineKeyboardButton(text="🚚 Створити накладну", callback_data=f"create_waybill_{order_id}")],
        [InlineKeyboardButton(text="❌ Відхилити замовлення", callback_data=f"decline_order_{order_id}")],
        [InlineKeyboardButton(text="🔙 Назад", callback_data="new_orders")]
    ])


def waybill_sender_selection(order_id, senders):
    """Create keyboard to select sender for waybill"""
    keyboard = []

    for sender in senders:
        keyboard.append([
            InlineKeyboardButton(
                text=f"{sender['name']} ({sender['phone']})",
                callback_data=f"select_sender_{sender['id']}_{order_id}"
            )
        ])

    keyboard.append([InlineKeyboardButton(text="🔙 Назад", callback_data=f"view_order_{order_id}")])

    return InlineKeyboardMarkup(inline_keyboard=keyboard)