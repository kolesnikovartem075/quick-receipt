package org.artem.order.dto;

import lombok.Value;

@Value
public class OrderReadDto {

    Long id;
    Long serviceId;
    Long userId;
    String description;
    String status;
}