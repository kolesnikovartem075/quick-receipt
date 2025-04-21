package org.waybill.order.dto;

import org.waybill.order.database.entity.OrderStatus;

import java.time.LocalDateTime;

public record OrderFilter(Long accountId,
                          Long userId,
                          OrderStatus status,
                          LocalDateTime dateCreated) {
}