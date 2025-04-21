package org.waybill.account.management.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.waybill.account.management.database.entity.Account;
import org.waybill.account.management.database.entity.Contact;
import org.waybill.account.management.database.repository.AccountRepository;
import org.waybill.account.management.dto.ContactCreateEditDto;
import org.waybill.account.management.service.CityService;

@Component
@RequiredArgsConstructor
public class ContactCreateEditMapper implements Mapper<ContactCreateEditDto, Contact> {

    private final AccountRepository accountRepository;
    private final CityService cityService;

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
        var cityRef = cityService.getCityRef(object.getCity());
        var account = getAccount(object);

        contact.setFirstName(object.getFirstName());
        contact.setLastName(object.getLastName());
        contact.setMiddleName(object.getMiddleName());
        contact.setPhoneNumber(object.getPhoneNumber());
        contact.setPostOfficeRef(object.getPostOfficeRef());
        contact.setCityRef(cityRef);
        contact.setAccount(account);
    }

    private Account getAccount(ContactCreateEditDto object) {
        return accountRepository.findById(object.getAccountId())
                .orElseThrow();
    }
}