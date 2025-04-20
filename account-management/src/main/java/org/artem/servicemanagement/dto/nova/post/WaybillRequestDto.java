package org.artem.servicemanagement.dto.nova.post;

import lombok.Data;
import org.artem.servicemanagement.dto.AccountSenderReadDto;
import org.artem.servicemanagement.dto.OrderReadDto;
import org.artem.servicemanagement.dto.UserContactReadDto;
import org.artem.servicemanagement.dto.UserProfileReadDto;

import java.time.LocalDateTime;

@Data
public class WaybillRequestDto {

    AccountSenderReadDto accountSender;
    UserContactReadDto user;
    OrderReadDto order;
    LocalDateTime createdAt;
}
