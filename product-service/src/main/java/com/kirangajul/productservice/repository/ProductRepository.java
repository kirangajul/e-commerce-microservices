package com.kirangajul.productservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kirangajul.productservice.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Integer> {
}
