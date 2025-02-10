from aiogram import F, Router
from aiogram.fsm.context import FSMContext
from aiogram.fsm.state import StatesGroup, State
from aiogram.types import Message, CallbackQuery

register_router = Router()


class Register(StatesGroup):
    first_name = State()
    last_name = State()
    number = State()
    nova_post_address = State()


@register_router.callback_query(F.data == 'register')
async def register(callback: CallbackQuery, state: FSMContext):
    await state.set_state(Register.first_name)
    await callback.answer('Давайте зареєструємо вас!')
    await callback.message.answer('Введіть ваше ім\'я:')


@register_router.message(Register.first_name)
async def fist_name(message: Message, state: FSMContext):
    await state.update_data(first_name=message.text)

    await state.set_state(Register.last_name)
    await message.answer('Введіть призвище:')


@register_router.message(Register.last_name)
async def last_name(message: Message, state: FSMContext):
    await state.update_data(last_name=message.text)

    await state.set_state(Register.nova_post_address)
    await message.answer('Введіть № відділення нової пошти:')


@register_router.message(Register.nova_post_address)
async def number(message: Message, state: FSMContext):
    await state.update_data(nova_post_address=message.text)

    await state.set_state(Register.number)
    await message.answer('Введіть телефон:')


@register_router.message(Register.number)
async def reg_three(message: Message, state: FSMContext):
    await state.update_data(number=message.text)
    data = await state.get_data()

    await message.answer(f'Імʼя: {data["first_name"]}\n'
                         f'Призвище: {data["last_name"]}\n'
                         f'Відділення нової пошти: {data["nova_post_address"]}\n'
                         f'Номер телефону: {message.text}\n'
                         f'Дякуємо за реєстрацію!')

    await state.clear()
