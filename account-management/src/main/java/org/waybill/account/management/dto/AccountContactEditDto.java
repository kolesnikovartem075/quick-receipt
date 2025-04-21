package org.waybill.account.management.dto;

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
