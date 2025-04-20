package org.artem.servicemanagement.mapper;


import lombok.RequiredArgsConstructor;
import org.artem.servicemanagement.database.entity.Account;
import org.artem.servicemanagement.database.entity.User;
import org.artem.servicemanagement.database.entity.UserRole;
import org.artem.servicemanagement.database.repository.AccountRepository;
import org.artem.servicemanagement.dto.UserCreateEditDto;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserCreateEditMapper implements Mapper<UserCreateEditDto, User> {

    private final AccountRepository accountRepository;

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
        var role = getRole(object);
        var account = getAccount(object);

        user.setExternalUserId(object.getExternalUserId());
        user.setRole(role);
        user.setAccount(account);
    }

    private Account getAccount(UserCreateEditDto object) {
        return accountRepository.findById(object.getAccountId())
                .orElseThrow();
    }

    private static UserRole getRole(UserCreateEditDto object) {
        return object.getRole() != null
                ? UserRole.valueOf(object.getRole())
                : UserRole.USER;
    }
}