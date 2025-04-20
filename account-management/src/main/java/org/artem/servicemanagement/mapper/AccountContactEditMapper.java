package org.artem.servicemanagement.mapper;

import lombok.RequiredArgsConstructor;
import org.artem.servicemanagement.database.entity.Account;
import org.artem.servicemanagement.database.entity.AccountContact;
import org.artem.servicemanagement.database.entity.Contact;
import org.artem.servicemanagement.database.repository.AccountRepository;
import org.artem.servicemanagement.dto.AccountContactEditDto;
import org.artem.servicemanagement.service.ContactService;
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