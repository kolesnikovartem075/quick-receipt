package org.artem.servicemanagement.mapper;

import lombok.RequiredArgsConstructor;
import org.artem.servicemanagement.database.entity.Contact;
import org.artem.servicemanagement.dto.AccountReadDto;
import org.artem.servicemanagement.dto.ContactReadDto;
import org.artem.servicemanagement.dto.nova.post.WarehouseReadDto;
import org.artem.servicemanagement.service.WarehouseService;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ContactReadMapper implements Mapper<Contact, ContactReadDto> {

    private final WarehouseService warehouseService;
    private final AccountReadMapper accountReadMapper;

    @Override
    public ContactReadDto map(Contact object) {
        var account = getAccount(object);
        var warehouse = getWarehouse(object);

        return ContactReadDto.builder()
                .id(object.getId())
                .firstName(object.getFirstName())
                .lastName(object.getLastName())
                .middleName(object.getMiddleName())
                .phoneNumber(object.getPhoneNumber())
                .warehouse(warehouse)
                .account(account)
                .dateCreated(object.getDateCreated())
                .dateUpdated(object.getDateUpdated())
                .build();
    }

    private AccountReadDto getAccount(Contact object) {
        return accountReadMapper.map(object.getAccount());
    }


    private WarehouseReadDto getWarehouse(Contact object) {
        return warehouseService.findByRef(object.getPostOfficeRef())
                .orElseThrow();
    }
}