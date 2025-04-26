from aiogram import Router
from aiogram.filters import CommandStart
from aiogram.fsm.context import FSMContext
from aiogram.fsm.state import StatesGroup, State
from aiogram.types import Message

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
    user = await fetch_user(message.from_user.id)

    if user and (user_contact := await fetch_user_contact(user.id)):
        await show_user_details(message, user_contact, state)
        return

    if not user:
        await create_user({"externalUserId": message.from_user.id})

    await message.reply(f'Привіт {message.from_user.first_name},\n'
                        f'Для оформлення доставки треба натиснути кнопку нижче!',
                        reply_markup=keyboard.register)
