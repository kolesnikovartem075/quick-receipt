package org.artem.user.mapper;

import lombok.RequiredArgsConstructor;
import org.artem.user.database.entity.User;
import org.artem.user.dto.UserCreateEditDto;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserCreateEditMapper implements Mapper<UserCreateEditDto, User> {


    @Override
    public User map(UserCreateEditDto fromObject, User toObject) {
        copy(fromObject, toObject);
        return toObject;
    }

    @Override
    public User map(UserCreateEditDto object) {
        User user = new User();
        copy(object, user);

        return user;
    }

    private void copy(UserCreateEditDto object, User user) {

        user.setExternalUserId(object.getExternalUserId());
    }
}