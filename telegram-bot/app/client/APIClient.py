import httpx
from httpx import Response
from typing import Optional

from app.config import account_id as default_account_id
from app.model.user_contact import UserRead, UserContactRead

ACCOUNT_MANAGEMENT_BASE_URL = "http://localhost:8080/api/v1"
ORDER_BASE_URL = "http://localhost:8081/api/v2"


async def fetch_user(telegram_id: int) -> Optional[UserRead]:
    async with httpx.AsyncClient() as client:
        response = await client.get(
            f"{ACCOUNT_MANAGEMENT_BASE_URL}/accounts/{default_account_id}/users",
            params={"externalUserId": telegram_id}
        )
        response.raise_for_status()

        content = await response.json().get("content", [])
        return UserRead.model_validate(content[0]) if content else None


async def fetch_user_contact(user_id: int) -> Optional[UserContactRead]:
    async with httpx.AsyncClient() as client:
        response = await client.get(
            f"{ACCOUNT_MANAGEMENT_BASE_URL}/accounts/{default_account_id}/users/{user_id}/contacts",
            params={"page": 0, "size": 1}
        )
        response.raise_for_status()

        content = await response.json().get("content", [])
        return UserContactRead.model_validate(content[0]) if content else None


async def create_user(user_data: dict) -> Response:
    async with httpx.AsyncClient() as client:
        response = await client.post(
            f"{ACCOUNT_MANAGEMENT_BASE_URL}/accounts/{default_account_id}/users",
            json=user_data
        )
        response.raise_for_status()
        return response


async def create_user_contact(user_id, contact_data: dict = None) -> Response:
    async with httpx.AsyncClient() as client:
        response = await client.post(
            f"{ACCOUNT_MANAGEMENT_BASE_URL}/accounts/{default_account_id}/users/{user_id}/contacts",
            json=contact_data
        )
        return response


async def create_order(order_data: dict) -> Response:
    async with httpx.AsyncClient() as client:
        response = await client.post(f"{ORDER_BASE_URL}/orders", json=order_data)
        return response
