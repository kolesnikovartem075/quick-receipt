package org.artem.servicemanagement.dto;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class AccountContactCreateDto {

    Long accountId;
    String apiKey;

    ContactCreateEditDto contactCreateEditDto;
}
