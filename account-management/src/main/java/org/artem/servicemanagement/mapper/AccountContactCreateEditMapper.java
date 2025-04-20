package org.artem.servicemanagement.mapper;

import lombok.RequiredArgsConstructor;
import org.artem.servicemanagement.database.entity.Account;
import org.artem.servicemanagement.database.entity.AccountContactProfile;
import org.artem.servicemanagement.database.entity.Contact;
import org.artem.servicemanagement.database.repository.AccountRepository;
import org.artem.servicemanagement.database.repository.ContactRepository;
import org.artem.servicemanagement.dto.AccountContactProfileCreateEditDto;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AccountContactCreateEditMapper implements Mapper<AccountContactProfileCreateEditDto, AccountContactProfile> {

    private final ContactRepository contactRepository;
    private final AccountRepository accountRepository;

    @Override
    public AccountContactProfile map(AccountContactProfileCreateEditDto fromObject, AccountContactProfile toObject) {
        copy(fromObject, toObject);
        return toObject;
    }

    @Override
    public AccountContactProfile map(AccountContactProfileCreateEditDto object) {
        AccountContactProfile profile = new AccountContactProfile();
        copy(object, profile);
        return profile;
    }

    private void copy(AccountContactProfileCreateEditDto object, AccountContactProfile profile) {
        var account = getAccount(object);
        var contact = getContact(object);

        profile.setApiKey(object.getApiKey());
        profile.setAccount(account);
        profile.setContact(contact);
    }

    private Contact getContact(AccountContactProfileCreateEditDto object) {
        return contactRepository.findById(object.getContactId()).orElseThrow();
    }

    private Account getAccount(AccountContactProfileCreateEditDto object) {
        return accountRepository.findById(object.getAccountId()).orElseThrow();
    }
}