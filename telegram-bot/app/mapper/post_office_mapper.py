from app.dto.user import PostOfficeReadDto


def map_post_office(post_office_data: dict) -> PostOfficeReadDto:
    return PostOfficeReadDto(
        ref=post_office_data.get('ref'),
        description=post_office_data.get('description'),
        short_address=post_office_data.get('shortAddress'),
        city_description=post_office_data.get('cityDescription')
    )
