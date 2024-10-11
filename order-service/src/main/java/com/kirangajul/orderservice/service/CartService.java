package com.kirangajul.orderservice.service;

import org.springframework.data.domain.Page;

import com.kirangajul.orderservice.dto.order.CartDto;

import reactor.core.publisher.Mono;

import java.util.List;

public interface CartService {
    Mono<List<CartDto>> findAll();

    Mono<Page<CartDto>> findAll(int page, int size, String sortBy, String sortOrder);

    Mono<CartDto> findById(Integer cartId);

    Mono<CartDto> save(final CartDto cartDto);

    Mono<CartDto> update(final CartDto cartDto);

    Mono<CartDto> update(final Integer cartId, final CartDto cartDto);

    Mono<Void> deleteById(final Integer cartId);
}
