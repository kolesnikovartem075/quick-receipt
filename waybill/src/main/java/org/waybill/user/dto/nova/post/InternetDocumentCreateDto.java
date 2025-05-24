package org.waybill.user.dto.nova.post;

import lombok.Builder;
import lombok.Data;
import org.waybill.user.dto.AccountContactReadDto;
import org.waybill.user.dto.OrderReadDto;
import org.waybill.user.dto.UserContactReadDto;

import java.time.LocalDateTime;

@Data
public class InternetDocumentCreateDto {

    private AccountContactReadDto accountContact;
    private UserContactReadDto user;
    private OrderReadDto order;
    private LocalDateTime createdAt;
    private PaymentPropertiesDto paymentProperties;
}
