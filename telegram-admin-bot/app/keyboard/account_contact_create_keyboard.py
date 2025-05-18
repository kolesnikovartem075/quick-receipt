from aiogram.types import InlineKeyboardMarkup, InlineKeyboardButton


def inline_cities(cities):
    """Generate keyboard for city selection"""
    return InlineKeyboardMarkup(inline_keyboard=[
        [InlineKeyboardButton(
            text=city.description,
            callback_data=f"city_{city.ref}|{city.description}"
        )]
        for city in cities
    ])


def inline_warehouses(warehouses):
    """Generate keyboard for warehouse selection"""
    return InlineKeyboardMarkup(inline_keyboard=[
        [InlineKeyboardButton(
            text=f"{i + 1}. {warehouse.description[:30]}",
            callback_data=f"warehouse_{warehouse.ref}"
        )]
        for i, warehouse in enumerate(warehouses)
    ])


confirm_action = InlineKeyboardMarkup(inline_keyboard=[
    [
        InlineKeyboardButton(text="✅ Підтвердити", callback_data="confirm_action"),
        InlineKeyboardButton(text="❌ Скасувати", callback_data="cancel_action")
    ]
])
