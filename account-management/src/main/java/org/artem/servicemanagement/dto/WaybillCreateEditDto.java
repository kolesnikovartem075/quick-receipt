package org.artem.servicemanagement.dto;

import lombok.Value;

@Value
public class WaybillCreateEditDto {

    private Long accountSenderId;
    private Long orderId;
}
