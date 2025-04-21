package org.waybill.account.management.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.waybill.account.management.database.entity.Account;
import org.waybill.account.management.database.entity.AccountContact;
import org.waybill.account.management.database.entity.Contact;
import org.waybill.account.management.database.repository.AccountRepository;
import org.waybill.account.management.dto.AccountContactCreateDto;
import org.waybill.account.management.service.CityService;
import org.waybill.account.management.service.ContactService;

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