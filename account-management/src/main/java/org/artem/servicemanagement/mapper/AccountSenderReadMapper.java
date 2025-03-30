package org.artem.servicemanagement.mapper;

import lombok.RequiredArgsConstructor;
import org.artem.servicemanagement.database.entity.AccountSender;
import org.artem.servicemanagement.dto.AccountReadDto;
import org.artem.servicemanagement.dto.AccountSenderReadDto;
import org.artem.servicemanagement.dto.nova.post.PostOfficeReadDto;
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
                .postOffice(warehouse)
                .account(account)
                .build();
    }

    private AccountReadDto getAccount(AccountSender object) {
        return accountReadMapper.map(object.getAccount());
    }

    private PostOfficeReadDto getWarehouse(AccountSender object) {
        return warehouseService.findByRef(object.getPostOfficeRef()).orElseThrow();
    }
}