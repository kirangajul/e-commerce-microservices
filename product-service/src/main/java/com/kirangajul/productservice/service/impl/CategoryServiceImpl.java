package com.kirangajul.productservice.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.kirangajul.productservice.dto.CategoryDto;
import com.kirangajul.productservice.entity.Category;
import com.kirangajul.productservice.exception.wrapper.CategoryNotFoundException;
import com.kirangajul.productservice.helper.CategoryMappingHelper;
import com.kirangajul.productservice.repository.CategoryRepository;
import com.kirangajul.productservice.repository.CategoryRepositoryPagingAndSorting;
import com.kirangajul.productservice.service.CategoryService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Transactional
@Slf4j
@RequiredArgsConstructor
@Service
public class CategoryServiceImpl implements CategoryService {
    private final ModelMapper modelMapper;

    @Autowired
    private final CategoryRepository categoryRepository;

    @Autowired
    private final CategoryRepositoryPagingAndSorting categoryRepositoryPagingAndSorting;

    @Override
    public List<CategoryDto> findAll() {
        log.info("Category List Service, fetch all category");
        
		try {
		
			return categoryRepository.findAll().stream().map(CategoryMappingHelper::map).distinct().collect(Collectors.toList());
			
		} catch (Exception e) {
			log.error("Error while fetching categories: " + e.getMessage());
			return Collections.emptyList();
		}
    }


//    @Override
//    public List<CategoryDto> findAll() {
//        log.info("*** CategoryDto List, service; fetch all categorys *");
//        return this.categoryRepository.findAll()
//                .stream()
//                .map(CategoryMappingHelper::map)
//                .distinct()
//                .collect(Collectors.toUnmodifiableList());
//    }


    @Override
    public Page<CategoryDto> findAllCategory(int page, int size) {
        log.info("*** CategoryDto List, service; fetch all categories ***");

        Pageable pageable = PageRequest.of(page, size);
        Page<Category> categoryPage = categoryRepository.findAll(pageable);

        List<CategoryDto> categoryDtos = categoryPage.getContent()
                .stream()
                .map(CategoryMappingHelper::map)
                .distinct()
                .collect(Collectors.toList());

        return new PageImpl<>(categoryDtos, pageable, categoryPage.getTotalElements());
    }


    // Paging and Sorting Categories
    @Override
    public List<CategoryDto> getAllCategories(Integer pageNo, Integer pageSize, String sortBy) {
        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));

        Page<Category> pagedResult = categoryRepositoryPagingAndSorting.findAllPagedAndSortedCategories(paging);

        if (pagedResult.hasContent()) {
            return pagedResult.getContent()
                    .stream()
                    .map((element) -> modelMapper.map(element, CategoryDto.class))
                    .collect(Collectors.toList());
        } else {
            return new ArrayList<>();
        }
    }


    @Override
    public CategoryDto findById(Integer categoryId) {
        log.info("CategoryDto Service, fetch category by id");
        return categoryRepository.findById(categoryId)
                .map(CategoryMappingHelper::map)
                .orElseThrow(() -> new CategoryNotFoundException(String.format("Category with id[%d] not found", categoryId)));
    }

    @Override
    public CategoryDto save(CategoryDto categoryDto) {
        log.info("CategoryDto, service; save category");
       
       try {
    	   Category category = CategoryMappingHelper.map(categoryDto);
           Category savedCategory = categoryRepository.save(category);
           return CategoryMappingHelper.map(savedCategory);
	} catch(DataIntegrityViolationException e) {
		throw new CategoryNotFoundException("Error saving category: Data integrity violation", e);
	}
       catch (Exception e) {
		throw new CategoryNotFoundException("Error saving category", e);
	}
    }
    
    @Override
    public List<CategoryDto> saveAll(List<CategoryDto> categoryDtos) {
      try {
		
    	  List<Category> categories = categoryDtos.stream().map(CategoryMappingHelper::map).collect(Collectors.toList());
    	  List<Category> savedCategories = categoryRepository.saveAll(categories);
    	  return savedCategories.stream().map(CategoryMappingHelper::map).collect(Collectors.toList());
	} catch (Exception e) {
		log.error("Error while saving categories: " + e.getMessage());
		return Collections.emptyList();
	}
    }

    @Override
    public CategoryDto update(CategoryDto categoryDto) {
        log.info("CategoryDto Service, update category");

        try {
            Category existingCategory = categoryRepository.findById(categoryDto.getCategoryId())
                    .orElseThrow(() -> new CategoryNotFoundException("Category not found with id: " + categoryDto.getCategoryId()));

            BeanUtils.copyProperties(categoryDto, existingCategory, "categoryId", "parentCategoryDto");

          

            return CategoryMappingHelper.map(categoryRepository.save(existingCategory));
        } catch (CategoryNotFoundException e) {
            log.error("Error updating category. Category with id [{}] not found.", categoryDto.getCategoryId());
            throw new CategoryNotFoundException(String.format("Category with id [%d] not found.", categoryDto.getCategoryId()), e);
        } catch (DataIntegrityViolationException e) {
            log.error("Error updating category: Data integrity violation", e);
            throw new CategoryNotFoundException("Error updating category: Data integrity violation", e);
        } catch (Exception e) {
            log.error("Error updating category", e);
            throw new CategoryNotFoundException("Error updating category", e);
        }
    }


    @Override
    public CategoryDto update(Integer categoryId, CategoryDto categoryDto) {
        log.info("CategoryDto Service: Updating category with categoryId");

        try {
            // Check if the category exists
            CategoryDto existingCategoryDto = this.findById(categoryId);

            // Convert CategoryDto to Category and update the fields
            Category existingCategory = CategoryMappingHelper.map(existingCategoryDto);
            BeanUtils.copyProperties(categoryDto, existingCategory, "categoryId", "parentCategoryDto");

            

            // Save the updated category to the database
            Category updatedCategory = categoryRepository.save(existingCategory);

            // Map the updated Category back to CategoryDto and return
            return CategoryMappingHelper.map(updatedCategory);
        } catch (CategoryNotFoundException e) {
            log.error("Error updating category. Category with id [{}] not found.", categoryId);
            throw new CategoryNotFoundException(String.format("Category with id [%d] not found.", categoryId), e);
        } catch (DataIntegrityViolationException e) {
            log.error("Error updating category: Data integrity violation", e);
            throw new CategoryNotFoundException("Error updating category: Data integrity violation", e);
        } catch (Exception e) {
            log.error("Error updating category", e);
            throw new CategoryNotFoundException("Error updating category", e);
        }
    }


    @Override
    public void deleteById(Integer categoryId) {
        log.info("Void Service, delete category by id");
        try {
            categoryRepository.deleteById(categoryId);
        } catch (CategoryNotFoundException e) {
            log.error("Error delete category", e);
            throw new CategoryNotFoundException("Error updating category", e);
        }
    }
}
