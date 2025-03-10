class PostOfficeReadDto:
    def __init__(self, ref: str, description: str, short_address: str, city_description: str):
        self.ref = ref
        self.description = description
        self.short_address = short_address
        self.city_description = city_description

    def __repr__(self):
        return f"PostOfficeReadDto(ref={self.ref}, description={self.description}, short_address={self.short_address}, city_description={self.city_description})"


class UserReadDto:
    def __init__(self, id: int, first_name: str, last_name: str, phone_number: str, post_office: PostOfficeReadDto):
        self.id = id
        self.first_name = first_name
        self.last_name = last_name
        self.phone_number = phone_number
        self.post_office = post_office

    def __repr__(self):
        return f"UserReadDto(id={self.id}, first_name={self.first_name}, last_name={self.last_name}, phone_number={self.phone_number}, post_office={self.post_office})"
