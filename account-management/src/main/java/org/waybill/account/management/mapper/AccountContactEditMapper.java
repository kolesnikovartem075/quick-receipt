package org.waybill.account.management.mapper;

import lombok.RequiredArgsConstructor;
import org.waybill.account.management.database.entity.Account;
import org.waybill.account.management.database.entity.AccountContact;
import org.waybill.account.management.database.entity.Contact;
import org.waybill.account.management.database.repository.AccountRepository;
import org.waybill.account.management.dto.AccountContactEditDto;
import org.waybill.account.management.service.ContactService;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AccountContactEditMapper implements EditMapper<AccountContactEditDto, AccountContact> {

    private final ContactService contactService;
    private final AccountRepository accountRepository;

    @Override
    public AccountContact map(AccountContactEditDto fromObject, AccountContact toObject) {
        copy(fromObject, toObject);
        return toObject;
    }

    private void copy(AccountContactEditDto object, AccountContact profile) {
        var account = getAccount(object);
        var contact = updateContact(object);

        profile.setApiKey(object.getApiKey());
        profile.setAccount(account);
        profile.setContact(contact);
    }

    private Contact updateContact(AccountContactEditDto object) {
        return contactService.update(object.getContactId(), object.getContactCreateEditDto())
                .orElseThrow();
    }

    private Account getAccount(AccountContactEditDto object) {
        return accountRepository.findById(object.getAccountId()).orElseThrow();
    }
}