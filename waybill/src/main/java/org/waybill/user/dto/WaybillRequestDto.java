package org.waybill.user.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class WaybillRequestDto {

    UserContactReadDto user;
    OrderReadDto order;
    LocalDateTime createdAt;
}
