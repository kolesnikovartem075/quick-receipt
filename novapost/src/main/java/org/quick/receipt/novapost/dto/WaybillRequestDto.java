package org.quick.receipt.novapost.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class WaybillRequestDto {

    AccountContactReadDto accountContact;
    UserContactReadDto user;
    OrderReadDto order;
    LocalDateTime createdAt;
}
