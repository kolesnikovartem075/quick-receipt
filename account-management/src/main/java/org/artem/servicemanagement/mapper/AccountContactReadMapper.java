package org.artem.servicemanagement.mapper;

import lombok.RequiredArgsConstructor;
import org.artem.servicemanagement.database.entity.AccountContactProfile;
import org.artem.servicemanagement.dto.AccountContactProfileReadDto;
import org.artem.servicemanagement.dto.ContactReadDto;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AccountContactReadMapper implements Mapper<AccountContactProfile, AccountContactProfileReadDto> {

    private final AccountReadMapper accountReadMapper;
    private final ContactReadMapper contactReadMapper;

    @Override
    public AccountContactProfileReadDto map(AccountContactProfile object) {
//        var account = getAccount(object);
        var contact = getContact(object);

        return AccountContactProfileReadDto.builder()
                .id(object.getId())
                .apiKey(object.getApiKey())
//                .account(account)
                .contact(contact)
                .dateCreated(object.getDateCreated())
                .dateUpdated(object.getDateUpdated())
                .build();
    }

    private ContactReadDto getContact(AccountContactProfile object) {
        return contactReadMapper.map(object.getContact());
    }

//    private AccountReadDto getAccount(AccountContactProfile object) {
//        return accountReadMapper.map(object.getAccount());
//    }
}
