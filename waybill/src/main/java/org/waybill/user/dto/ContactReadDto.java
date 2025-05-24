package org.waybill.user.dto;

import lombok.Builder;
import lombok.Value;
import org.waybill.user.dto.nova.post.WarehouseReadDto;

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