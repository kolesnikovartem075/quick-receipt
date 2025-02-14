import re

from aiogram import F, Router
from aiogram.fsm.context import FSMContext
from aiogram.fsm.state import StatesGroup, State
from aiogram.types import Message, CallbackQuery

from app.keyboards import confirm_nova_post_registration

register_router = Router()


class Register(StatesGroup):
    first_name = State()
    last_name = State()
    number = State()
    nova_post_address = State()
    message_id = State()


async def update_registration_message(message: Message, state: FSMContext):
    data = await state.get_data()

    text = (
        "📋 *Ваша інформація:* \n\n"
        f"👤 Ім'я: {data.get('first_name', '❌ Не вказано')}\n"
        f"👤 Призвище: {data.get('last_name', '❌ Не вказано')}\n"
        f"🏢 Відділення Нової Пошти: {data.get('nova_post_address', '❌ Не вказано')}\n"
        f"📞 Телефон: {data.get('number', '❌ Не вказано')}\n\n"
    )

    message_id = data.get("message_id")
    if message_id:
        await message.bot.edit_message_text(text, chat_id=message.chat.id, message_id=int(message_id),
                                            parse_mode="Markdown")
    else:
        new_message = await message.answer(text, parse_mode="Markdown")
        await state.update_data(message_id=new_message.message_id)


@register_router.callback_query(F.data == 'register')
async def register(callback: CallbackQuery, state: FSMContext):
    await state.set_state(Register.first_name)
    await callback.answer("Давайте зареєструємо вас!")

    await update_registration_message(callback.message, state)
    await callback.message.answer('Введіть ваше імʼя:')


@register_router.message(Register.first_name)
async def first_name(message: Message, state: FSMContext):
    await state.update_data(first_name=message.text)
    await state.set_state(Register.last_name)

    await update_registration_message(message, state)
    await message.answer('Введіть ваше прізвище:')


@register_router.message(Register.last_name)
async def last_name(message: Message, state: FSMContext):
    await state.update_data(last_name=message.text)
    await state.set_state(Register.nova_post_address)

    await update_registration_message(message, state)
    await message.answer('Введіть № відділення нової пошти:')


@register_router.message(Register.nova_post_address)
async def nova_post_address(message: Message):
    await message.answer(f"Ваше відділення: {message.text}\n\nПідтвердити?",
                         reply_markup=confirm_nova_post_registration)


@register_router.callback_query(F.data == 'confirm_nova_post')
async def confirm_nova_post(callback: CallbackQuery, state: FSMContext):
    await callback.message.edit_reply_markup(reply_markup=None)

    match = re.search(r"Ваше відділення:\s*(.+)", callback.message.text)
    await state.update_data(nova_post_address=match.group(1))
    await state.set_state(Register.number)
    await callback.answer()
    await update_registration_message(callback.message, state)
    await callback.message.answer('Введіть номер телефону:')


@register_router.callback_query(F.data == 'retry_nova_post')
async def last_name(callback: CallbackQuery):
    await callback.message.edit_reply_markup(reply_markup=None)
    await callback.message.answer('Введіть № відділення нової пошти:')


@register_router.message(Register.number)
async def register_finish(message: Message, state: FSMContext):
    await state.update_data(number=message.text)
    data = await state.get_data()

    await message.answer(
        f"✅ *Реєстрація завершена!*\n\n"
        f"👤 Імʼя: {data['first_name']}\n"
        f"👤 Призвище: {data['last_name']}\n"
        f"🏢 Відділення Нової Пошти: {data['nova_post_address']}\n"
        f"📞 Номер телефону: {data['number']}\n"
        "Дякуємо за реєстрацію!",
        parse_mode="Markdown"
    )

    await state.clear()