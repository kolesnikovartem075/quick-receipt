import asyncio
import logging
import os

from aiogram import Bot, Dispatcher
from dotenv import load_dotenv

from app.handler.dashboard import dashboard_router
from app.handler.order import order_router
from app.handler.sender import sender_router
from app.handler.setting import settings_router
from app.handler.user import user_router
from app.handler.waybill import waybill_router


async def main():
    load_dotenv()
    bot = Bot(token=os.getenv('BOT_TOKEN'))
    dp = Dispatcher()
    dp.include_routers(dashboard_router, settings_router, order_router, user_router, waybill_router, sender_router)

    await dp.start_polling(bot)


if __name__ == '__main__':
    logging.basicConfig(level=logging.INFO)
    try:
        asyncio.run(main())
    except KeyboardInterrupt:
        print('Bot stopped')
