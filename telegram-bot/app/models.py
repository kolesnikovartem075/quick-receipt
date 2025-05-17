from datetime import datetime
from pydantic import BaseModel, ConfigDict
from pydantic.alias_generators import to_camel
from typing import Optional


class BaseSchema(BaseModel):
    model_config = ConfigDict(
        alias_generator=to_camel,
        populate_by_name=True,
        from_attributes=True,
    )


class CityRead(BaseSchema):
    description: str
    descriptionRu: str
    ref: str
    pass


class WarehouseRead(BaseSchema):
    ref: str
    description: str
    short_address: str
    city: CityRead


class ContactRead(BaseSchema):
    id: int
    first_name: str
    last_name: str
    middle_name: Optional[str]
    phone_number: str
    warehouse: WarehouseRead
    date_created: Optional[datetime]
    date_updated: Optional[datetime]


class UserRead(BaseSchema):
    id: int
    external_user_id: int
    account_id: int
    role: str
    date_created: Optional[datetime]
    date_updated: Optional[datetime]


class UserContactRead(BaseSchema):
    id: int
    user: UserRead
    contact: ContactRead
    date_created: Optional[datetime]
    date_updated: Optional[datetime]


class OrderRead(BaseSchema):
    id: int
    account_id: int
    user_id: int
    description: str
    status: str
