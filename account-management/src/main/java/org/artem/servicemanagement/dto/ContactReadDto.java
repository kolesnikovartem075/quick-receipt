package org.artem.servicemanagement.dto;

import lombok.Builder;
import lombok.Value;
import org.artem.servicemanagement.dto.nova.post.WarehouseReadDto;

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
    AccountReadDto account;
    LocalDateTime dateCreated;
    LocalDateTime dateUpdated;
}
