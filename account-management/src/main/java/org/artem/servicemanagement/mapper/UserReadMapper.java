package org.artem.servicemanagement.mapper;


import lombok.RequiredArgsConstructor;
import org.artem.servicemanagement.database.entity.User;
import org.artem.servicemanagement.dto.AccountReadDto;
import org.artem.servicemanagement.dto.UserReadDto;
import org.artem.servicemanagement.service.AccountService;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserReadMapper implements Mapper<User, UserReadDto> {


    private final AccountService accountService;

    @Override
    public UserReadDto map(User object) {
        var account = getAccount(object);

        return UserReadDto.builder()
                .id(object.getId())
                .externalUserId(object.getExternalUserId())
                .role(object.getRole().name())
                .account(account)
                .build();
    }

    private AccountReadDto getAccount(User object) {
        return accountService.findById(object.getAccount().getId())
                .orElseThrow();
    }
}