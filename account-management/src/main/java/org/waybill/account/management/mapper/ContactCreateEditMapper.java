package org.waybill.account.management.mapper;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.waybill.account.management.database.entity.Account;
import org.waybill.account.management.database.entity.Contact;
import org.waybill.account.management.database.repository.AccountRepository;
import org.waybill.account.management.dto.ContactCreateEditDto;
import org.waybill.account.management.dto.nova.post.WarehouseReadDto;
import org.waybill.account.management.dto.nova.post.WarehouseRequestDto;
import org.waybill.account.management.service.CityService;
import org.waybill.account.management.service.WarehouseService;

@Component
@RequiredArgsConstructor
public class ContactCreateEditMapper implements Mapper<ContactCreateEditDto, Contact> {

    private final AccountRepository accountRepository;
    private final CityService cityService;
    private final WarehouseService warehouseService;

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
        var cityRef = getCityRef(object);
        var account = getAccount(object);
        var warehouseReadDto = getWarehouseRef(cityRef, object);

        contact.setFirstName(object.getFirstName());
        contact.setLastName(object.getLastName());
        contact.setMiddleName(object.getMiddleName());
        contact.setPhoneNumber(object.getPhoneNumber());
        contact.setPostOfficeRef(warehouseReadDto.getRef());
        contact.setCityRef(cityRef);
        contact.setAccount(account);
    }

    private String  getCityRef(ContactCreateEditDto object) {
        return object.getCityRef() != null
                ? object.getCityRef()
                : cityService.getCityRef(object.getCity() + " ");
    }

    private Account getAccount(ContactCreateEditDto object) {
        return accountRepository.findById(object.getAccountId())
                .orElseThrow();
    }


    private WarehouseReadDto getWarehouseRef(String cityRef, ContactCreateEditDto object) {
        var request = new WarehouseRequestDto();
        if (StringUtils.isNotBlank(object.getWarehouseRef())) {
            request.setRef(object.getWarehouseRef());
        } else {
            request.setFindByString(getFindByString(object));

        }

        request.setCityRef(cityRef);
        return warehouseService.findBy(request)
                .orElseThrow();
    }

    private String getFindByString(ContactCreateEditDto object) {
        var numberOfThePostOffice = StringUtils.getDigits(object.getPostOffice());
        return String.format("№%s ", numberOfThePostOffice);
    }
}