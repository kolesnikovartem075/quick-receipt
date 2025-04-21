package org.waybill.account.management.dto;

import lombok.Builder;
import lombok.Value;

import java.time.LocalDateTime;

@Value
@Builder
public class UserContactReadDto {

    Long id;
    UserReadDto user;
    ContactReadDto contact;

    LocalDateTime dateCreated;
    LocalDateTime dateUpdated;
}
