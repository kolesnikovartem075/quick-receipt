package org.artem.order.dto;

import lombok.Value;

@Value
public class OrderCreateEditDto {

    Long accountId;
    Long userContactId;
    String description;
    String status;
}