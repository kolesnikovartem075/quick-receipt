package org.waybill.account.management.mapper;

import lombok.RequiredArgsConstructor;
import org.waybill.account.management.database.entity.Account;
import org.waybill.account.management.database.entity.AccountStatus;
import org.waybill.account.management.dto.AccountCreateEditDto;
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
        var status = getStatus(object);

        account.setNickname(object.getNickname());
        account.setName(object.getName());
        account.setStatus(status);
    }

    private AccountStatus getStatus(AccountCreateEditDto object) {
        return object.getStatus() != null
                ? AccountStatus.valueOf(object.getStatus())
                : AccountStatus.ACTIVE;
    }
}