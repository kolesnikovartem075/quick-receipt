from typing import Optional, List

import httpx
from httpx import Response

from app.models import AccountRead, UserRead, WarehouseRead, CityRead, AccountContactRead, UserContactRead

ACCOUNT_MANAGEMENT_BASE_URL = "http://localhost:8080/api/v1"
ORDER_BASE_URL = "http://localhost:8081/api/v1"
NOVA_POSHTA_BASE_URL = "http://localhost:8090/api/v1"


async def fetch_user(telegram_id: int) -> Optional[UserRead]:
    """Fetch a user by their Telegram ID"""
    async with httpx.AsyncClient() as client:
        response = await client.get(
            f"{ACCOUNT_MANAGEMENT_BASE_URL}/users",
            params={"externalUserId": telegram_id, "page": 0, "size": 1}
        )
        response.raise_for_status()

        content = response.json().get("content", [])
        return UserRead.model_validate(content[0]) if content else None


async def fetch_user_by_id(user_id: int) -> Optional[UserRead]:
    async with httpx.AsyncClient() as client:
        response = await client.get(
            f"{ACCOUNT_MANAGEMENT_BASE_URL}/users/{user_id}"
        )
        response.raise_for_status()

        content = response.json().get("content", [])
        return UserRead.model_validate(content[0]) if content else None


async def fetch_user_contact(user_id: int) -> Optional[UserContactRead]:
    async with httpx.AsyncClient() as client:
        response = await client.get(
            f"{ACCOUNT_MANAGEMENT_BASE_URL}/users/{user_id}/contacts",
            params={"page": 0, "size": 1}
        )
        response.raise_for_status()

        content = response.json().get("content", [])
        return UserContactRead.model_validate(content[0]) if content else None


async def fetch_account(account_id: int) -> Optional[AccountRead]:
    """Fetch account details"""
    async with httpx.AsyncClient() as client:
        response = await client.get(
            f"{ACCOUNT_MANAGEMENT_BASE_URL}/accounts/{account_id}"
        )
        if response.status_code == 404:
            return None
        response.raise_for_status()
        return AccountRead.model_validate(response.json())


async def fetch_cities(query_string: str) -> List[CityRead]:
    async with httpx.AsyncClient() as client:
        response = await client.get(f"{NOVA_POSHTA_BASE_URL}/cities/{query_string}")
        response.raise_for_status()

        return [CityRead.model_validate(city) for city in response.json()]


async def fetch_warehouses(request_data: dict) -> List[WarehouseRead]:
    async with httpx.AsyncClient() as client:
        response = await client.get(f"{NOVA_POSHTA_BASE_URL}/warehouses", params=request_data)
        response.raise_for_status()

        return [WarehouseRead.model_validate(city) for city in response.json()]


async def create_account_contact(account_id, contact_data: dict) -> Response:
    async with httpx.AsyncClient() as client:
        response = await client.post(
            f"{ACCOUNT_MANAGEMENT_BASE_URL}/accounts/{account_id}/contacts",
            json=contact_data
        )
        return response


async def fetch_account_contacts(account_id: int) -> List[AccountContactRead]:
    async with httpx.AsyncClient() as client:
        response = await client.get(
            f"{ACCOUNT_MANAGEMENT_BASE_URL}/accounts/{account_id}/contacts",
            params={"page": 0, "size": 10}
        )
        response.raise_for_status()

        content = response.json().get("content", [])
        return [AccountContactRead.model_validate(city) for city in content]
