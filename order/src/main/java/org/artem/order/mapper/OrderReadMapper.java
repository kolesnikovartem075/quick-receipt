package org.artem.order.mapper;

import lombok.RequiredArgsConstructor;
import org.artem.order.database.entity.Order;
import org.artem.order.dto.OrderReadDto;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderReadMapper implements Mapper<Order, OrderReadDto> {


    @Override
    public OrderReadDto map(Order object) {

        return new OrderReadDto();
    }
}