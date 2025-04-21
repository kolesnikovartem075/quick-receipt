from aiogram import F, Router
from aiogram.filters import Command, CommandStart
from aiogram.fsm.context import FSMContext
from aiogram.fsm.state import StatesGroup, State
from aiogram.types import Message, CallbackQuery

from app.client.APIClient import create_user, fetch_user_contact, fetch_user
from app.handler.order import show_user_details
from app.keyboard import keyboard
from app.middlewares import TestMiddleware

router = Router()

router.message.middleware(TestMiddleware())


class Register(StatesGroup):
    name = State()
    number = State()


@router.message(CommandStart())
async def start(message: Message, state: FSMContext):
    telegram_user_id = message.from_user.id
    user = await fetch_user(telegram_user_id)
    user_contact = await fetch_user_contact(user.id)
    if user_contact:
        await show_user_details(message, user_contact, state)
        return

    user_data = {"externalUserId": telegram_user_id}
    await create_user(user_data)
    await message.reply(f'Привіт {message.from_user.first_name},\n'
                        f'Для оформлення доставки треба натиснути кнопку нижче!',
                        reply_markup=keyboard.register)


@router.message(F.text == 'Как дела?')
async def how_are_you(message: Message):
    await message.answer('Хорошо, а у тебя?')


@router.message(F.photo)
async def photo(message: Message):
    await message.answer(f'ID photo: {message.photo[-1].file_id}')


@router.message(Command('get_photo'))
async def get_photo(message: Message):
    await message.answer_photo(
        photo='AgACAgIAAxkBAAMMZ5ZIW8x3wtXh6f7h4HfU-jAGpgsAAgvpMRuH5nBJ-wHYP741gawBAAMCAAN5AAM2BA',
        caption='Photo caption')


@router.callback_query(F.data == 'catalog')
async def catalog(callback: CallbackQuery):
    await callback.answer('catalog pressed')
    await callback.message.edit_text('catalog pressed', reply_markup=await keyboard.inline_cars())
