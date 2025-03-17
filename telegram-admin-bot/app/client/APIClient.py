import httpx

SERVICE_BASE_URL = "http://localhost:8081/api/v1"


def get_dashboard_stats():
    return None


def get_orders():
    response = await fetch_admin(telegram_id)

    if response.status_code == 200:
        data = response.json()
        content = data.get("content", [])
        if content:
            return map_user(content[0])

    return None


def get_order_by_id():
    return None


def update_order_status():
    return None


def get_user_by_id():
    return None


def get_users():
    return None


def get_service_senders():
    return None


def get_service_sender_by_id():
    return None


def create_waybill():
    return None


async def get_admin(telegram_id: str):
    response = await fetch_admin(telegram_id)

    if response.status_code == 200:
        data = response.json()
        content = data.get("content", [])
        if content:
            return content[0]

    return None


async def fetch_admin(telegram_id: str):
    async with httpx.AsyncClient() as client:
        response = await client.get(
            f"{SERVICE_BASE_URL}/user-profiles",
            params={"telegramId": telegram_id, "page": 0, "size": 1}
        )
        return response