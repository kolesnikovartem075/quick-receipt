package org.waybill.order.service;

import lombok.RequiredArgsConstructor;
import org.waybill.order.database.repository.OrderRepository;
import org.waybill.order.database.specification.OrderSpecification;
import org.waybill.order.dto.OrderCreateEditDto;
import org.waybill.order.dto.OrderFilter;
import org.waybill.order.dto.OrderReadDto;
import org.waybill.order.mapper.OrderCreateEditMapper;
import org.waybill.order.mapper.OrderReadMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderReadMapper orderReadMapper;
    private final OrderCreateEditMapper orderCreateEditMapper;


    public Optional<OrderReadDto> findById(Long id) {
        return orderRepository.findById(id)
                .map(orderReadMapper::map);
    }

    public Page<OrderReadDto> findAll(OrderFilter orderFilter, Pageable pageable) {
        var specification = new OrderSpecification(orderFilter);
        return orderRepository.findAll(specification, pageable)
                .map(orderReadMapper::map);
    }

    @Transactional
    public OrderReadDto create(OrderCreateEditDto orderDto) {
        return Optional.of(orderDto)
                .map(orderCreateEditMapper::map)
                .map(orderRepository::save)
                .map(orderReadMapper::map)
                .orElseThrow();
    }

    @Transactional
    public Optional<OrderReadDto> update(Long id, OrderCreateEditDto orderDto) {
        return orderRepository.findById(id)
                .map(entity -> orderCreateEditMapper.map(orderDto, entity))
                .map(orderRepository::saveAndFlush)
                .map(orderReadMapper::map);
    }

    @Transactional
    public boolean delete(Long id) {
        return orderRepository.findById(id)
                .map(entity -> {
                    orderRepository.delete(entity);
                    orderRepository.flush();
                    return true;
                })
                .orElse(false);
    }
}