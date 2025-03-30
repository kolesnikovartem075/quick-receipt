package org.artem.servicemanagement.dto;

import lombok.Value;

@Value
public class OrderReadDto {

    Long id;
    Long accountId;
    Long userId;
    String description;
    String status;
}
