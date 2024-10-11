package com.kirangajul.productservice.service;

import java.util.List;

import com.kirangajul.productservice.dto.ProductDto;

public interface ProductService {
//    List<ProductDto> findAll();
    List<ProductDto> findAll();

    ProductDto findById(final Integer productId);

    ProductDto save(final ProductDto productDto);

    ProductDto update(final ProductDto productDto);

    ProductDto update(final Integer productId, final ProductDto productDto);

    Boolean deleteById(final Integer productId);

	List<ProductDto> saveAll(List<ProductDto> productDtos);
}
