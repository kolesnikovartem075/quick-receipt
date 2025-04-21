package org.waybill.account.management.dto.nova.post;

import lombok.Value;

import java.time.LocalDateTime;

@Value
public class WaybillCreateEditDto {

    Long accountSenderId;
    Long orderId;
    LocalDateTime createdAt;
}
