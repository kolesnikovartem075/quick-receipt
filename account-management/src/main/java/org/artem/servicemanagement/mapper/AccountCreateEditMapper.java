package org.artem.servicemanagement.mapper;

import lombok.RequiredArgsConstructor;
import org.artem.servicemanagement.database.entity.Account;
import org.artem.servicemanagement.dto.AccountCreateEditDto;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AccountCreateEditMapper implements Mapper<AccountCreateEditDto, Account> {


    @Override
    public Account map(AccountCreateEditDto fromObject, Account toObject) {
        copy(fromObject, toObject);
        return toObject;
    }

    @Override
    public Account map(AccountCreateEditDto object) {
        Account account = new Account();
        copy(object, account);

        return account;
    }

    private void copy(AccountCreateEditDto object, Account account) {
        account.setNickname(object.getNickname());
        account.setName(object.getName());
    }
}
