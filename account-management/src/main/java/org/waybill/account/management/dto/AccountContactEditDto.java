package org.waybill.account.management.dto;

import lombok.Data;

@Data
public class AccountContactEditDto {

    Long accountId;
    Long contactId;
    String apiKey;

    ContactCreateEditDto contactCreateEditDto;
}
