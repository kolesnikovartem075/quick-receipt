from aiogram import F, Router
from aiogram.filters import CommandStart, Command
from aiogram.fsm.context import FSMContext
from aiogram.fsm.state import StatesGroup, State
from aiogram.types import Message, CallbackQuery

import app.keyboards as kb
from app.middlewares import TestMiddleware

router = Router()

router.message.middleware(TestMiddleware())


class Reg(StatesGroup):
    name = State()
    number = State()


@router.message(CommandStart())
async def start(message: Message):
    await message.reply(f'Hi! \nYour ID: {message.from_user.id}\n Your username: {message.from_user.username}',
                        reply_markup=kb.main)


@router.message(Command('help'))
async def get_help(message: Message):
    await message.answer('Help message')


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
    await callback.message.edit_text('catalog pressed', reply_markup=await kb.inline_cars())


@router.message(Command('reg'))
async def reg_one(message: Message, state: FSMContext):
    await state.set_state(Reg.name)
    await message.answer('Enter your name')


@router.message(Reg.name)
async def reg_two(message: Message, state: FSMContext):
    await state.update_data(name=message.text)
    await state.set_state(Reg.number)
    await message.answer('Enter your number')


@router.message(Reg.number)
async def reg_three(message: Message, state: FSMContext):
    await state.update_data(number=message.text)
    data = await state.get_data()
    await message.answer(f'Name: {data["name"]}\nNumber: {message.text}')
    await state.clear()
