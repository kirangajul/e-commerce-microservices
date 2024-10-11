package com.kirangajul.orderservice.service;

import org.springframework.data.domain.Page;

import com.kirangajul.orderservice.dto.order.OrderDto;

import reactor.core.publisher.Mono;

import java.util.List;

public interface OrderService {
    Mono<List<OrderDto>> findAll();

    Mono<Page<OrderDto>> findAll(int page, int size, String sortBy, String sortOrder);

    Mono<OrderDto> findById(Integer orderId);

    Mono<OrderDto> save(final OrderDto orderDto);

    Mono<OrderDto> update(final OrderDto orderDto);

    Mono<OrderDto> update(final Integer orderId, final OrderDto orderDto);

    Mono<Void> deleteById(final Integer orderId);

    Boolean existsByOrderId(Integer orderId);
}
