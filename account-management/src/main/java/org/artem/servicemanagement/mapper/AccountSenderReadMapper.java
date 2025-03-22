package org.artem.servicemanagement.mapper;

import lombok.RequiredArgsConstructor;
import org.artem.servicemanagement.database.entity.AccountSender;
import org.artem.servicemanagement.dto.AccountReadDto;
import org.artem.servicemanagement.dto.AccountSenderReadDto;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AccountSenderReadMapper implements Mapper<AccountSender, AccountSenderReadDto> {


    private final AccountReadMapper accountReadMapper;

    @Override
    public AccountSenderReadDto map(AccountSender object) {
        var account = getAccount(object);

        return AccountSenderReadDto.builder()
                .id(object.getId())
                .firstName(object.getFirstName())
                .lastName(object.getLastName())
                .phoneNumber(object.getPhoneNumber())
                .postOfficeRef(object.getPostOfficeRef())
                .account(account)
                .build();
    }

    private AccountReadDto getAccount(AccountSender object) {
        return accountReadMapper.map(object.getAccount());
    }
}