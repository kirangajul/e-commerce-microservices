package com.hoangtien2k3.productservice.helper;

import com.hoangtien2k3.productservice.entity.Category;
import com.hoangtien2k3.productservice.dto.CategoryDto;

import java.util.Optional;

public interface CategoryMappingHelper {

	static CategoryDto map(final Category category) {

		return CategoryDto.builder().categoryId(category.getCategoryId()).categoryTitle(category.getCategoryTitle())
				.imageUrl(category.getImageUrl()).build();
	}

	static Category map(CategoryDto categoryDto) {
		return Category.builder().categoryId(categoryDto.getCategoryId()).categoryTitle(categoryDto.getCategoryTitle())
				.imageUrl(categoryDto.getImageUrl()).build();
	}

}
