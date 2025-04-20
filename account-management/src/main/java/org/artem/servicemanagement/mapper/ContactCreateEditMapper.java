package org.artem.servicemanagement.mapper;

import lombok.RequiredArgsConstructor;
import org.artem.servicemanagement.database.entity.Account;
import org.artem.servicemanagement.database.entity.Contact;
import org.artem.servicemanagement.database.repository.AccountRepository;
import org.artem.servicemanagement.dto.ContactCreateEditDto;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ContactCreateEditMapper implements Mapper<ContactCreateEditDto, Contact> {

    private final AccountRepository accountRepository;

    @Override
    public Contact map(ContactCreateEditDto fromObject, Contact toObject) {
        copy(fromObject, toObject);
        return toObject;
    }

    @Override
    public Contact map(ContactCreateEditDto object) {
        Contact contact = new Contact();
        copy(object, contact);
        return contact;
    }

    private void copy(ContactCreateEditDto object, Contact contact) {
        var account = getAccount(object);

        contact.setFirstName(object.getFirstName());
        contact.setLastName(object.getLastName());
        contact.setMiddleName(object.getMiddleName());
        contact.setPhoneNumber(object.getPhoneNumber());
        contact.setPostOfficeRef(object.getPostOfficeRef());
        contact.setCityRef(object.getCityRef());
        contact.setAccount(account);
    }

    private Account getAccount(ContactCreateEditDto object) {
        return accountRepository.findById(object.getAccountId())
                .orElseThrow();
    }
}