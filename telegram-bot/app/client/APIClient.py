import httpx
from httpx import Response
from typing import Optional

from app.dto.user import UserReadDto
from app.mapper.user_mapper import map_user

ACCOUNT_MANAGEMENT_BASE_URL = "http://localhost:8083/api/v1"
ORDER_BASE_URL = "http://localhost:8081/api/v2"


async def get_user_by_telegram_id(telegram_id: int) -> Optional[UserReadDto]:
    response = await fetch_user(telegram_id)

    if response.status_code == 200:
        data = response.json()
        content = data.get("content", [])
        if content:
            return map_user(content[0])

    return None


async def fetch_user(telegram_id: int):
    async with httpx.AsyncClient() as client:
        response = await client.get(
            f"{ACCOUNT_MANAGEMENT_BASE_URL}/users",
            params={"externalUserId": telegram_id, "page": 0, "size": 1}
        )
        return response


async def fetch_user_contact(user_id: int):
    async with httpx.AsyncClient() as client:
        response = await client.get(
            f"{ACCOUNT_MANAGEMENT_BASE_URL}/users/{user_id}/contacts",
            params={"externalUserId": user_id, "page": 0, "size": 1}
        )
        return response


async def create_user(, telegram_id: int) -> Optional[UserReadDto]:
    async with httpx.AsyncClient() as client:
        response = await client.post(f"{ACCOUNT_MANAGEMENT_BASE_URL}/users/{user_id}/contacts", json=user_data)
        return response


async def create_user_contact(user_id, user_data: dict):
    async with httpx.AsyncClient() as client:
        response = await client.post(f"{ACCOUNT_MANAGEMENT_BASE_URL}/users/{user_id}/contacts", json=user_data)
        return response


async def create_order(order_data: dict) -> Response:
    async with httpx.AsyncClient() as client:
        response = await client.post(f"{ACCOUNT_MANAGEMENT_BASE_URL}/orders", json=order_data)
        return response
