from typing import Optional

import httpx

from app.models import AccountRead, UserRead

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
