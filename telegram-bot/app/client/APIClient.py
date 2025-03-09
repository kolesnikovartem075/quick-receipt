from typing import Union, Any, Optional, Coroutine

import httpx

from app.dto.user import UserReadDto
from app.mapper.user_mapper import map_user

BASE_URL = "http://localhost:8081/api/v1"


async def get_user_by_telegram_id(telegram_id: str) -> Optional[UserReadDto]:
    response = await fetch_user_data(telegram_id)

    if response.status_code == 200:
        data = response.json()
        content = data.get("content", [])
        if content:
            return map_user(content[0])

    return None


async def fetch_user_data(telegram_id: str):
    async with httpx.AsyncClient() as client:
        response = await client.get(
            f"{BASE_URL}/users",
            params={"telegramId": telegram_id, "page": 0, "size": 1}
        )
        return response


async def create_user(user_data: dict):
    async with httpx.AsyncClient() as client:
        response = await client.post(f"{BASE_URL}/users", json=user_data)
        return response
