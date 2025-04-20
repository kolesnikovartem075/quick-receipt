package org.artem.servicemanagement.dto;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class AccountContactEditDto {

    Long accountId;
    Long contactId;
    String apiKey;

    ContactCreateEditDto contactCreateEditDto;
}
