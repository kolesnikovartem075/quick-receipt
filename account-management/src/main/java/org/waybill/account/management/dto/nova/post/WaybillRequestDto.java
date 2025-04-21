package org.waybill.account.management.dto.nova.post;

import lombok.Data;
import org.waybill.account.management.dto.OrderReadDto;
import org.waybill.account.management.dto.UserContactReadDto;

import java.time.LocalDateTime;

@Data
public class WaybillRequestDto {

    UserContactReadDto user;
    OrderReadDto order;
    LocalDateTime createdAt;
}
