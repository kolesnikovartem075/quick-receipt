import httpx
from httpx import Response
from typing import Optional

from app.dto.user import UserReadDto
from app.mapper.user_mapper import map_user

USER_BASE_URL = "http://localhost:8081/api/v1"


async def get_user_by_telegram_id(telegram_id: int) -> Optional[UserReadDto]:
    response = await fetch_user_data(telegram_id)

    if response.status_code == 200:
        data = response.json()
        content = data.get("content", [])
        if content:
            return map_user(content[0])

    return None


async def fetch_user_data(telegram_id: int):
    async with httpx.AsyncClient() as client:
        response = await client.get(
            f"{USER_BASE_URL}/user-profiles",
            params={"telegramId": telegram_id, "page": 0, "size": 1}
        )
        return response


async def create_user(user_data: dict):
    async with httpx.AsyncClient() as client:
        response = await client.post(f"{USER_BASE_URL}/user-profiles", json=user_data)
        return response


async def create_order(order_data: dict) -> Response:
    async with httpx.AsyncClient() as client:
        response = await client.post(f"{USER_BASE_URL}orders", json=order_data)
        return response
