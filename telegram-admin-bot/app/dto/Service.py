from typing import Dict, Any


class Service:
    def __init__(self, service_id: int, name: str, api_url: str, api_key: str):
        self.service_id = service_id
        self.name = name
        self.api_url = api_url
        self.api_key = api_key

    @classmethod
    def from_dict(cls, data: Dict[str, Any]):
        return cls(
            service_id=data.get('id'),
            name=data.get('name'),
            api_url=data.get('api_url'),
            api_key=data.get('api_key')
        )

    def to_dict(self):
        return {
            'id': self.id,
            'name': self.name,
            'api_url': self.api_url,
            'api_key': self.api_key
        }
