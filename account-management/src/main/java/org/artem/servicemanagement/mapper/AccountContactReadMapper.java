package org.artem.servicemanagement.mapper;

import lombok.RequiredArgsConstructor;
import org.artem.servicemanagement.database.entity.AccountContact;
import org.artem.servicemanagement.dto.AccountContactReadDto;
import org.artem.servicemanagement.dto.ContactReadDto;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AccountContactReadMapper implements Mapper<AccountContact, AccountContactReadDto> {

    private final AccountReadMapper accountReadMapper;
    private final ContactReadMapper contactReadMapper;

    @Override
    public AccountContactReadDto map(AccountContact object) {
//        var account = getAccount(object);
        var contact = getContact(object);

        return AccountContactReadDto.builder()
                .id(object.getId())
                .apiKey(object.getApiKey())
//                .account(account)
                .contact(contact)
                .dateCreated(object.getDateCreated())
                .dateUpdated(object.getDateUpdated())
                .build();
    }

    private ContactReadDto getContact(AccountContact object) {
        return contactReadMapper.map(object.getContact());
    }

//    private AccountReadDto getAccount(AccountContactProfile object) {
//        return accountReadMapper.map(object.getAccount());
//    }
}
