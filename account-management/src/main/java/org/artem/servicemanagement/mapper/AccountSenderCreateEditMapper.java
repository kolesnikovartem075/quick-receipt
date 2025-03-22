package org.artem.servicemanagement.mapper;

import lombok.RequiredArgsConstructor;
import org.artem.servicemanagement.database.entity.Account;
import org.artem.servicemanagement.database.entity.AccountSender;
import org.artem.servicemanagement.database.repository.AccountRepository;
import org.artem.servicemanagement.dto.AccountSenderCreateEditDto;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AccountSenderCreateEditMapper implements Mapper<AccountSenderCreateEditDto, AccountSender> {


    private final AccountRepository accountRepository;

    @Override
    public AccountSender map(AccountSenderCreateEditDto fromObject, AccountSender toObject) {
        copy(fromObject, toObject);
        return toObject;
    }

    @Override
    public AccountSender map(AccountSenderCreateEditDto object) {
        AccountSender accountSender = new AccountSender();
        copy(object, accountSender);

        return accountSender;
    }

    private void copy(AccountSenderCreateEditDto object, AccountSender accountSender) {
        var account = getAccount(object);
        accountSender.setAccount(account);
        accountSender.setFirstName(object.getFirstName());
        accountSender.setLastName(object.getLastName());
        accountSender.setPhoneNumber(object.getPhoneNumber());
        accountSender.setPostOfficeRef(object.getPostOfficeRef());
    }

    private Account getAccount(AccountSenderCreateEditDto object) {
        return accountRepository.findById(object.getAccountId()).orElseThrow();
    }
}