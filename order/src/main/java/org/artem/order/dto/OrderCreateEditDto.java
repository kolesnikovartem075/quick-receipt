package org.artem.order.dto;

import lombok.Value;

@Value
public class OrderCreateEditDto {

    Long accountId;
    Long userId;
    String description;
    String status;
}