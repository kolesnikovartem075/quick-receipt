from aiogram.types import InlineKeyboardMarkup, InlineKeyboardButton
from aiogram.utils.keyboard import InlineKeyboardBuilder

register = InlineKeyboardMarkup(inline_keyboard=[
    [InlineKeyboardButton(text="Зареєструватись", callback_data="register")]
])

confirm_nova_post_registration = InlineKeyboardMarkup(inline_keyboard=[
    [InlineKeyboardButton(text="✅ Підтвердити", callback_data="confirm_nova_post"),
     InlineKeyboardButton(text="🔄 Ввести знову", callback_data="retry_nova_post")]
])

main = InlineKeyboardMarkup(inline_keyboard=[
    [InlineKeyboardButton(text='Catalog', callback_data='catalog')],
    [InlineKeyboardButton(text='Basket', callback_data='basket'),
     InlineKeyboardButton(text='Contacts', callback_data='contacts')],
])

settings = InlineKeyboardMarkup(inline_keyboard=[
    [InlineKeyboardButton(text='Change name', callback_data='change_name')],
])

cars = ['Tesla', 'BMW', 'Audi', 'Toyota', 'Lada', 'Kia']


async def inline_cars():
    keyboard = InlineKeyboardBuilder()
    for car in cars:
        keyboard.add(InlineKeyboardButton(text=car, callback_data=f'car_{car}'))
    return keyboard.adjust(2).as_markup()
