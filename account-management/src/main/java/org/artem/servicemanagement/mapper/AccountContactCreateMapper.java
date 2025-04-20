package org.artem.servicemanagement.mapper;

import lombok.RequiredArgsConstructor;
import org.artem.servicemanagement.database.entity.Account;
import org.artem.servicemanagement.database.entity.AccountContact;
import org.artem.servicemanagement.database.entity.Contact;
import org.artem.servicemanagement.database.repository.AccountRepository;
import org.artem.servicemanagement.dto.AccountContactCreateDto;
import org.artem.servicemanagement.service.ContactService;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AccountContactCreateMapper implements CreateMapper<AccountContactCreateDto, AccountContact> {

    private final ContactService contactService;
    private final AccountRepository accountRepository;

    @Override
    public AccountContact map(AccountContactCreateDto object) {
        AccountContact profile = new AccountContact();
        copy(object, profile);
        return profile;
    }

    private void copy(AccountContactCreateDto object, AccountContact profile) {
        var account = getAccount(object);
        var contact = createContact(object);

        profile.setApiKey(object.getApiKey());
        profile.setAccount(account);
        profile.setContact(contact);
    }

    private Contact createContact(AccountContactCreateDto object) {
        return contactService.create(object.getContactCreateEditDto());
    }

    private Account getAccount(AccountContactCreateDto object) {
        return accountRepository.findById(object.getAccountId()).orElseThrow();
    }
}