package com.kirangajul.productservice.service;

import reactor.core.publisher.Flux;

import java.util.List;

import com.kirangajul.productservice.dto.ProductDto;

public interface ProductService {
//    List<ProductDto> findAll();
    Flux<List<ProductDto>> findAll();

    ProductDto findById(final Integer productId);

    ProductDto save(final ProductDto productDto);

    ProductDto update(final ProductDto productDto);

    ProductDto update(final Integer productId, final ProductDto productDto);

    void deleteById(final Integer productId);
}
