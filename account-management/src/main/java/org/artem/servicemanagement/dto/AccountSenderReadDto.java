package org.artem.servicemanagement.dto;

import lombok.Builder;
import lombok.Value;
import org.artem.servicemanagement.dto.nova.post.WarehouseReadDto;

@Value
@Builder
public class AccountSenderReadDto {

    Long id;
    AccountReadDto account;
    String firstName;
    String lastName;
    String phoneNumber;
    WarehouseReadDto warehouseReadDto;
}