package org.artem.order.dto;

import lombok.Value;

@Value
public class OrderCreateEditDto {

    Long serviceId;
    Long userId;
    String description;
    String status;
}