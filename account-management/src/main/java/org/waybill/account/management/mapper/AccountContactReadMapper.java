package org.waybill.account.management.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.waybill.account.management.database.entity.AccountContact;
import org.waybill.account.management.dto.AccountContactReadDto;
import org.waybill.account.management.dto.ContactReadDto;

@Component
@RequiredArgsConstructor
public class AccountContactReadMapper implements Mapper<AccountContact, AccountContactReadDto> {

    private final ContactReadMapper contactReadMapper;

    @Override
    public AccountContactReadDto map(AccountContact object) {
        var contact = getContact(object);

        return AccountContactReadDto.builder()
                .id(object.getId())
                .apiKey(object.getApiKey())
                .contact(contact)
                .dateCreated(object.getDateCreated())
                .dateUpdated(object.getDateUpdated())
                .build();
    }

    private ContactReadDto getContact(AccountContact object) {
        return contactReadMapper.map(object.getContact());
    }
}
