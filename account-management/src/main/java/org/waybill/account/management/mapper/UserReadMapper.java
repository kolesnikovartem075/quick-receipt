package org.waybill.account.management.mapper;


import lombok.RequiredArgsConstructor;
import org.waybill.account.management.database.entity.User;
import org.waybill.account.management.dto.UserReadDto;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserReadMapper implements Mapper<User, UserReadDto> {


    @Override
    public UserReadDto map(User object) {
        return UserReadDto.builder()
                .id(object.getId())
                .accountId(object.getAccount().getId())
                .externalUserId(object.getExternalUserId())
                .role(object.getRole().name())
                .build();
    }
}