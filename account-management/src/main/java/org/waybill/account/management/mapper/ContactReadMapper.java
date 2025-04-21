package org.waybill.account.management.mapper;

import lombok.RequiredArgsConstructor;
import org.waybill.account.management.database.entity.Contact;
import org.waybill.account.management.dto.ContactReadDto;
import org.waybill.account.management.dto.nova.post.WarehouseReadDto;
import org.waybill.account.management.service.WarehouseService;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ContactReadMapper implements Mapper<Contact, ContactReadDto> {

    private final WarehouseService warehouseService;

    @Override
    public ContactReadDto map(Contact object) {
        var warehouse = getWarehouse(object);

        return ContactReadDto.builder()
                .id(object.getId())
                .firstName(object.getFirstName())
                .lastName(object.getLastName())
                .middleName(object.getMiddleName())
                .phoneNumber(object.getPhoneNumber())
                .warehouse(warehouse)
                .dateCreated(object.getDateCreated())
                .dateUpdated(object.getDateUpdated())
                .build();
    }

    private WarehouseReadDto getWarehouse(Contact object) {
        return warehouseService.findByRef(object.getPostOfficeRef())
                .orElseThrow();
    }
}