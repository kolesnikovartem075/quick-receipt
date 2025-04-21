package org.waybill.order.dto;

import lombok.Value;

@Value
public class OrderCreateEditDto {

    Long accountId;
    Long userContactId;
    String description;
    String status;
}