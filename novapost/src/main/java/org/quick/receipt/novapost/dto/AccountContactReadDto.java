package org.quick.receipt.novapost.dto;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Value;

import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@Value
@Builder
public class AccountContactReadDto extends CounterpartyContactDto {

    Long id;
    ContactReadDto contact;
    String apiKey;
    LocalDateTime dateCreated;
    LocalDateTime dateUpdated;
}