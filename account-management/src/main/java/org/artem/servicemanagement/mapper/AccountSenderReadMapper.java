package org.artem.servicemanagement.mapper;

import lombok.RequiredArgsConstructor;
import org.artem.servicemanagement.database.entity.AccountSender;
import org.artem.servicemanagement.dto.AccountReadDto;
import org.artem.servicemanagement.dto.AccountSenderReadDto;
import org.artem.servicemanagement.dto.nova.post.WarehouseReadDto;
import org.artem.servicemanagement.dto.nova.post.WarehouseRequestDto;
import org.artem.servicemanagement.service.WarehouseService;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AccountSenderReadMapper implements Mapper<AccountSender, AccountSenderReadDto> {


    private final WarehouseService warehouseService;
    private final AccountReadMapper accountReadMapper;

    @Override
    public AccountSenderReadDto map(AccountSender object) {
        var account = getAccount(object);
        var warehouse = getWarehouse(object);


        return AccountSenderReadDto.builder()
                .id(object.getId())
                .firstName(object.getFirstName())
                .lastName(object.getLastName())
                .phoneNumber(object.getPhoneNumber())
                .warehouseReadDto(warehouse)
                .account(account)
                .build();
    }

    private AccountReadDto getAccount(AccountSender object) {
        return accountReadMapper.map(object.getAccount());
    }

    private WarehouseReadDto getWarehouse(AccountSender object) {
        var warehouseRequestDto = new WarehouseRequestDto();
        warehouseRequestDto.setRef(object.getPostOfficeRef());

        return warehouseService.findBy(warehouseRequestDto).orElseThrow();
    }
}