package org.quick.receipt.novapost.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class WaybillRequestDto {

    AccountSenderReadDto accountSender;
    UserProfileReadDto user;
    OrderReadDto order;
    LocalDateTime createdAt;
}
