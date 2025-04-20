package org.artem.servicemanagement.dto;

import lombok.Builder;
import lombok.Value;

import java.time.LocalDateTime;

@Value
@Builder
public class AccountContactProfileReadDto {


    Long id;
    ContactReadDto contact;
    String apiKey;
    LocalDateTime dateCreated;
    LocalDateTime dateUpdated;
}
