package org.quick.receipt.novapost.dto;

import lombok.Builder;
import lombok.Value;

import java.time.LocalDateTime;

@Value
@Builder
public class ContactReadDto {

    Long id;
    String firstName;
    String lastName;
    String middleName;
    String phoneNumber;
    WarehouseReadDto warehouse;
    LocalDateTime dateCreated;
    LocalDateTime dateUpdated;
}