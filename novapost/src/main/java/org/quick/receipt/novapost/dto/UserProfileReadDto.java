package org.quick.receipt.novapost.dto;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class UserProfileReadDto extends CounterpartyContactDto {

    Long id;
    Long externalUserId;
    Long accountId;
    String firstName;
    String lastName;
    String phoneNumber;
    WarehouseReadDto warehouseReadDto;
}
