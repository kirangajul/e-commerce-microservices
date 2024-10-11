package com.kirangajul.productservice.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.kirangajul.productservice.entity.Category;

public interface CategoryRepository extends JpaRepository<Category, Integer> {

    Page<Category> findAll(Pageable pageable);

  
    Page<Category> findByCategoryTitleContaining(String categoryTitle, Pageable pageable);

}
