package org.waybill.order.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.waybill.order.database.entity.Order;
import org.waybill.order.database.entity.OrderStatus;
import org.waybill.order.dto.OrderCreateEditDto;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class OrderCreateEditMapper implements Mapper<OrderCreateEditDto, Order> {


    @Override
    public Order map(OrderCreateEditDto fromObject, Order toObject) {
        copy(fromObject, toObject);
        return toObject;
    }

    @Override
    public Order map(OrderCreateEditDto object) {
        Order order = new Order();
        copy(object, order);

        return order;
    }

    private void copy(OrderCreateEditDto object, Order order) {
        var status = getStatus(object);

        order.setDescription(object.getDescription());
        order.setAccountId(object.getAccountId());
        order.setUserContactId(object.getUserContactId());
        order.setStatus(status);
    }

    private OrderStatus getStatus(OrderCreateEditDto object) {
        return Optional.ofNullable(object.getStatus())
                .map(OrderStatus::valueOf)
                .orElse(OrderStatus.CREATED);
    }
}