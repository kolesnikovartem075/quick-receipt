package org.artem.user.mapper;

import lombok.RequiredArgsConstructor;
import org.artem.user.database.entity.User;
import org.artem.user.dto.UserReadDto;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserReadMapper implements Mapper<User, UserReadDto> {


    @Override
    public UserReadDto map(User object) {
        return UserReadDto.builder()
                .id(object.getId())
                .externalUserId(object.getExternalUserId())
                .build();
    }
}