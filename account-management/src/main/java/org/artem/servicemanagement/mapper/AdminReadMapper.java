package org.artem.servicemanagement.mapper;

import lombok.RequiredArgsConstructor;
import org.artem.servicemanagement.database.entity.Admin;
import org.artem.servicemanagement.dto.AccountReadDto;
import org.artem.servicemanagement.dto.AdminReadDto;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AdminReadMapper implements Mapper<Admin, AdminReadDto> {

    private final AccountReadMapper accountReadMapper;

    @Override
    public AdminReadDto map(Admin object) {
        var account = getAccount(object);

        return AdminReadDto.builder()
                .id(object.getId())
                .account(account)
                .role(object.getRole().name())
                .build();
    }

    private AccountReadDto getAccount(Admin object) {
        return accountReadMapper.map(object.getAccount());
    }
}
