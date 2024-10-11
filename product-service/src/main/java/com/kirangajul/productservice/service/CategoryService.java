package com.kirangajul.productservice.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.kirangajul.productservice.dto.CategoryDto;

public interface CategoryService {

    List<CategoryDto> findAll();

    Page<CategoryDto> findAllCategory(int page, int size);
    List<CategoryDto> getAllCategories(Integer pageNo, Integer pageSize, String sortBy);



    CategoryDto findById(final Integer categoryId);

    CategoryDto save(final CategoryDto categoryDto);

    CategoryDto update(final CategoryDto categoryDto);

    CategoryDto update(final Integer categoryId, final CategoryDto categoryDto);

    void deleteById(final Integer categoryId);

	List<CategoryDto> saveAll(List<CategoryDto> categoryDtos);

}
