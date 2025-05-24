package org.waybill.user.dto;

import lombok.Builder;
import lombok.Value;

import java.time.LocalDateTime;

@Value
@Builder
public class AccountContactReadDto {


    Long id;
    ContactReadDto contact;
    String apiKey;
    LocalDateTime dateCreated;
    LocalDateTime dateUpdated;
}
