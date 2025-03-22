package org.artem.servicemanagement.mapper;

import lombok.RequiredArgsConstructor;
import org.artem.servicemanagement.database.entity.Account;
import org.artem.servicemanagement.dto.AccountReadDto;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AccountReadMapper implements Mapper<Account, AccountReadDto> {


    @Override
    public AccountReadDto map(Account object) {
        return AccountReadDto.builder()
                .id(object.getId())
                .name(object.getName())
                .nickname(object.getNickname())
                .build();
    }
}
