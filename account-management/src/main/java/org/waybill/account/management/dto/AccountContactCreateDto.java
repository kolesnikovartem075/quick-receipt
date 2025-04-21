package org.waybill.account.management.dto;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class AccountContactCreateDto {

    Long accountId;
    String apiKey;

    ContactCreateEditDto contactCreateEditDto;
}
