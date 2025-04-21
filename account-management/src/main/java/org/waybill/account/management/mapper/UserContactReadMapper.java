package org.waybill.account.management.mapper;

import lombok.RequiredArgsConstructor;
import org.waybill.account.management.database.entity.UserContact;
import org.waybill.account.management.dto.ContactReadDto;
import org.waybill.account.management.dto.UserContactReadDto;
import org.waybill.account.management.dto.UserReadDto;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserContactReadMapper implements Mapper<UserContact, UserContactReadDto> {

    private final UserReadMapper userReadMapper;
    private final ContactReadMapper contactReadMapper;

    @Override
    public UserContactReadDto map(UserContact object) {
        var user = getUser(object);
        var contact = getContact(object);

        return UserContactReadDto.builder()
                .id(object.getId())
                .user(user)
                .contact(contact)
                .dateCreated(object.getDateCreated())
                .dateUpdated(object.getDateUpdated())
                .build();
    }

    private UserReadDto getUser(UserContact object) {
        return userReadMapper.map(object.getUser());
    }

    private ContactReadDto getContact(UserContact object) {
        return contactReadMapper.map(object.getContact());
    }
}