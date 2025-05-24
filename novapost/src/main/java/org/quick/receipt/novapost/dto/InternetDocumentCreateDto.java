package org.quick.receipt.novapost.dto;

import lombok.Value;

import java.time.LocalDateTime;

@Value
public class InternetDocumentCreateDto {

    AccountContactReadDto accountContact;
    UserContactReadDto user;
    OrderReadDto order;
    LocalDateTime createdAt;
    PaymentPropertiesDto paymentProperties;
}
