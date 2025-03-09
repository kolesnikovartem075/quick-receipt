package org.artem.order.dto;

import org.artem.order.database.entity.OrderStatus;

import java.time.LocalDateTime;

public record OrderFilter(Long serviceId,
                          Long userId,
                          OrderStatus status,
                          LocalDateTime dateCreated) {
}