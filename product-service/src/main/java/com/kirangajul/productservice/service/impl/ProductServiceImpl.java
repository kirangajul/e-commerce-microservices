package com.kirangajul.productservice.service.impl;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.kirangajul.productservice.dto.ProductDto;
import com.kirangajul.productservice.entity.Category;
import com.kirangajul.productservice.entity.Product;
import com.kirangajul.productservice.exception.wrapper.ProductNotFoundException;
import com.kirangajul.productservice.helper.ProductMappingHelper;
import com.kirangajul.productservice.repository.CategoryRepository;
import com.kirangajul.productservice.repository.ProductRepository;
import com.kirangajul.productservice.service.ProductService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Transactional
@Slf4j
@RequiredArgsConstructor
@Service
public class ProductServiceImpl implements ProductService {

	@Autowired
	private final ProductRepository productRepository;

	@Autowired
	private final CategoryRepository categoryRepository;

	@Override
	public List<ProductDto> findAll() {
		log.info("ProductDto List, service, fetch all products");
		try {
			List<ProductDto> productDtos = productRepository.findAll().stream().map(ProductMappingHelper::map)
					.distinct().collect(Collectors.toList());
			return productDtos;
		} catch (Exception e) {
			log.error("Error while fetching products: " + e.getMessage());
			return Collections.emptyList();
		}
	}

	@Override
	public ProductDto findById(Integer productId) {
		log.info("ProductDto, service; fetch product by id");
		return productRepository.findById(productId).map(ProductMappingHelper::map).orElseThrow(
				() -> new ProductNotFoundException(String.format("Product with id[%d] not found", productId)));
	}

	@Override
	public ProductDto save(ProductDto productDto) {
		log.info("ProductDto, service; save product");
		try {
			Product product = ProductMappingHelper.map(productDto); // Convert ProductDto to Product entity
			Product savedProduct = productRepository.save(product); // Save product synchronously
			return ProductMappingHelper.map(savedProduct); // Convert saved Product entity back to ProductDto
		} catch (DataIntegrityViolationException e) {
			throw new ProductNotFoundException("Error saving product: Data integrity violation", e);
		} catch (Exception e) {
			throw new ProductNotFoundException("Error saving product", e);
		}
	}

	@Override
	public List<ProductDto> saveAll(List<ProductDto> productDtos) {
		log.info("ProductDto List, service; save all products");
		try {
			List<Product> products = productDtos.stream().map(ProductMappingHelper::map).collect(Collectors.toList());
			List<Product> savedProducts = productRepository.saveAll(products);
			return savedProducts.stream().map(ProductMappingHelper::map).collect(Collectors.toList());
		} catch (Exception e) {
			log.error("Error while saving products: " + e.getMessage());
			return Collections.emptyList();
		}
	}

	@Override
	public ProductDto update(ProductDto productDto) {
	    log.info("ProductDto, service; update product");

	    Product existingProduct = productRepository.findById(productDto.getProductId())
	            .orElseThrow(() -> new ProductNotFoundException("Product not found with id: " + productDto.getProductId()));

	    BeanUtils.copyProperties(productDto, existingProduct, "productId", "category");

	    if (productDto.getCategoryId() != null) {
	        Category category = categoryRepository.findById(productDto.getCategoryId())
	                .orElseThrow(() -> new ProductNotFoundException("Category not found with id: " + productDto.getCategoryId()));
	        existingProduct.setCategory(category);
	    }

	    Product updatedProduct = productRepository.save(existingProduct);

	    return ProductMappingHelper.map(updatedProduct);
	}


	@Override
	public ProductDto update(Integer productId, ProductDto productDto) {
		log.info("ProductDto, service; update product with productId");

		// Find existing product or throw exception if not found
		Product existingProduct = productRepository.findById(productId)
				.orElseThrow(() -> new ProductNotFoundException("Product not found with id: " + productId));

		// Copy properties from DTO to existing product entity, except 'productId' and
		// 'category'
		BeanUtils.copyProperties(productDto, existingProduct, "productId", "category");

		// Update category if provided
		if (productDto.getCategoryId() != null) {
			Category category = Category.builder().categoryId(productDto.getCategoryId()).build();
			existingProduct.setCategory(category);
		}

		// Save updated product entity
		Product updatedProduct = productRepository.save(existingProduct);

		// Map updated product entity back to DTO
		return ProductMappingHelper.map(updatedProduct);
	}

	@Override
	public Boolean deleteById(Integer productId) {
		log.info("Void, service; delete product by id");

		Product existingProduct = productRepository.findById(productId)
				.orElseThrow(() -> new ProductNotFoundException("Product not found with id: " + productId));

		productRepository.delete(existingProduct);

		return true;
	}

}
