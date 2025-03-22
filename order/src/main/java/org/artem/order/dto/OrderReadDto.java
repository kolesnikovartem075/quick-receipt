package org.artem.order.dto;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class OrderReadDto {

    Long id;
    Long accountId;
    Long userId;
    String description;
    String status;
}