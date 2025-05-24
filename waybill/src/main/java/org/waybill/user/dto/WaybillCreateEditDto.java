package org.waybill.user.dto;

import lombok.Value;

import java.time.LocalDateTime;

@Value
public class WaybillCreateEditDto {

    Long accountSenderId;
    Long orderId;
    LocalDateTime createdAt;
}
