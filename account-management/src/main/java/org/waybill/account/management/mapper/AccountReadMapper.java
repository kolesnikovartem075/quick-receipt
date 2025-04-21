package org.waybill.account.management.mapper;

import lombok.RequiredArgsConstructor;
import org.waybill.account.management.database.entity.Account;
import org.waybill.account.management.dto.AccountContactReadDto;
import org.waybill.account.management.dto.AccountReadDto;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class AccountReadMapper implements Mapper<Account, AccountReadDto> {

    private final AccountContactReadMapper accountContactReadMapper;

    @Override
    public AccountReadDto map(Account object) {
        var contactProfiles = getContactProfiles(object);

        return AccountReadDto.builder()
                .id(object.getId())
                .name(object.getName())
                .nickname(object.getNickname())
                .status(object.getStatus().name())
                .contactProfiles(contactProfiles)
                .build();
    }

    private List<AccountContactReadDto> getContactProfiles(Account object) {
        return object.getContactProfiles().stream()
                .map(accountContactReadMapper::map)
                .toList();
    }
}