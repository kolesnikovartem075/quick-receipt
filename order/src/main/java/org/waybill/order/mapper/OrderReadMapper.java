package org.waybill.order.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.waybill.order.database.entity.Order;
import org.waybill.order.dto.OrderReadDto;

@Component
@RequiredArgsConstructor
public class OrderReadMapper implements Mapper<Order, OrderReadDto> {


    @Override
    public OrderReadDto map(Order object) {

        return OrderReadDto.builder()
                .id(object.getId())
                .accountId(object.getAccountId())
                .userId(object.getUserContactId())
                .description(object.getDescription())
                .status(object.getStatus().name())
                .build();
    }
}