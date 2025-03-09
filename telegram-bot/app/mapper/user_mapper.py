from app.dto.user import UserReadDto
from app.mapper.post_office_mapper import map_post_office


def map_user(user_data: dict) -> UserReadDto:
    post_office = map_post_office(user_data.get('postOffice', {}))
    return UserReadDto(
        id=user_data.get('id'),
        first_name=user_data.get('firstName'),
        last_name=user_data.get('las    tName'),
        phone_number=user_data.get('phoneNumber'),
        post_office=post_office
    )
