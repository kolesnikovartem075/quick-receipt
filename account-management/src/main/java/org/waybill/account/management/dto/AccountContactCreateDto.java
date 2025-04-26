package org.waybill.account.management.dto;

import lombok.Data;

@Data
public class AccountContactCreateDto {

    Long accountId;
    String apiKey;

    ContactCreateEditDto contactCreateEditDto;
}
