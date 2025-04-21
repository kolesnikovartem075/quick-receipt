package org.waybill.account.management.dto;

import lombok.Value;

@Value
public class OrderReadDto {

    Long id;
    Long accountId;
    Long userContactId;
    String description;
    String status;
}
