package org.artem.order.controller;

import lombok.RequiredArgsConstructor;
import org.artem.order.dto.OrderCreateEditDto;
import org.artem.order.dto.OrderFilter;
import org.artem.order.dto.OrderReadDto;
import org.artem.order.service.OrderService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import static org.springframework.http.ResponseEntity.noContent;
import static org.springframework.http.ResponseEntity.notFound;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
public class OrderController {


    private final OrderService orderService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Page<OrderReadDto> findAll(OrderFilter orderFilter, Pageable pageable) {
        return orderService.findAll(orderFilter, pageable);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public OrderReadDto findById(@PathVariable Long id) {
        return orderService.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public OrderReadDto create(@RequestBody OrderCreateEditDto order) {
        return orderService.create(order);
    }

    @PutMapping("/{id}")
    public OrderReadDto update(@PathVariable Long id,
                              @RequestBody OrderCreateEditDto order) {
        return orderService.update(id, order)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        return orderService.delete(id)
                ? noContent().build()
                : notFound().build();
    }
}
