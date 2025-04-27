from aiogram.types import InlineKeyboardMarkup, InlineKeyboardButton
from aiogram.utils.keyboard import InlineKeyboardBuilder

# Basic keyboards
register = InlineKeyboardMarkup(inline_keyboard=[
    [InlineKeyboardButton(text="Зареєструватись", callback_data="register")]
])

confirm_nova_post_registration = InlineKeyboardMarkup(inline_keyboard=[
    [InlineKeyboardButton(text="✅ Підтвердити", callback_data="confirm_nova_post"),
     InlineKeyboardButton(text="🔄 Ввести знову", callback_data="retry_nova_post")]
])

order_confirmation = InlineKeyboardMarkup(inline_keyboard=[
    [InlineKeyboardButton(text="✏️ Редагувати", callback_data="edit_user"),
     InlineKeyboardButton(text="📦 Оформити доставку", callback_data="confirm_order")]
])

main = InlineKeyboardMarkup(inline_keyboard=[
    [InlineKeyboardButton(text='Catalog', callback_data='catalog')],
    [InlineKeyboardButton(text='Basket', callback_data='basket'),
     InlineKeyboardButton(text='Contacts', callback_data='contacts')],
])

settings = InlineKeyboardMarkup(inline_keyboard=[
    [InlineKeyboardButton(text='Change name', callback_data='change_name')],
])


# Dynamic keyboard functions
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


cars = ['Tesla', 'BMW', 'Audi', 'Toyota', 'Lada', 'Kia']

async def inline_cars():
    """Build a dynamic keyboard with car options"""
    keyboard = InlineKeyboardBuilder()
    for car in cars:
        keyboard.add(InlineKeyboardButton(text=car, callback_data=f'car_{car}'))
    return keyboard.adjust(2).as_markup()
