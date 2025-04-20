package org.quick.receipt.novapost.dto;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Value;

@EqualsAndHashCode(callSuper = true)
@Value
@Builder
public class AccountSenderReadDto extends CounterpartyContactDto {

    Long id;
    String firstName;
    String lastName;
    String phoneNumber;
    WarehouseReadDto warehouseReadDto;
}