from typing import Dict, Any

from app.dto.Service import Service


class Admin:
    def __init__(self, admin_id: int, telegram_id: int, name: str, role: str, service: Service):
        self.admin_id = admin_id
        self.telegram_id = telegram_id
        self.name = name
        self.role = role
        self.service = service

    @classmethod
    def from_dict(cls, data: Dict[str, Any]):
        service = Service.from_dict(data.get('service', {}))
        return cls(
            admin_id=data.get('id'),
            telegram_id=data.get('telegram_id'),
            name=data.get('name'),
            role=data.get('role'),
            service=service
        )

    def to_dict(self):
        return {
            'id': self.admin_id,
            'telegram_id': self.telegram_id,
            'name': self.name,
            'role': self.role,
            'service': self.service.to_dict()
        }
