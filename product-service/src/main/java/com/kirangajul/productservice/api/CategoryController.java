package com.kirangajul.productservice.api;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kirangajul.productservice.dto.CategoryDto;
import com.kirangajul.productservice.service.CategoryService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * REST controller for managing categories.
 * This class provides CRUD operations and other category-related functionalities.
 */
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    @Autowired
    private final CategoryService categoryService;

    /**
     * Retrieves a list of all categories.
     * 
     * @return ResponseEntity containing a list of CategoryDto objects representing all categories.
     */
    @GetMapping
    public ResponseEntity<List<CategoryDto>> findAll() {
        log.info("Fetching all categories");
        return ResponseEntity.ok(categoryService.findAll());
    }

    /**
     * Retrieves a paginated list of categories.
     * 
     * @param page Page number to retrieve (default is 0).
     * @param size Number of categories per page (default is 10).
     * @return ResponseEntity containing a Page of CategoryDto objects.
     */
    @GetMapping("/paging")
    public ResponseEntity<Page<CategoryDto>> getAllCategories(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        log.info("Fetching paginated categories: page {}, size {}", page, size);
        Page<CategoryDto> categoryPage = categoryService.findAllCategory(page, size);
        return new ResponseEntity<>(categoryPage, HttpStatus.OK);
    }

    /**
     * Retrieves a paginated and sorted list of categories.
     * 
     * @param pageNo Page number to retrieve (default is 0).
     * @param pageSize Number of categories per page (default is 10).
     * @param sortBy Field by which to sort the categories (default is categoryId).
     * @return ResponseEntity containing a list of sorted CategoryDto objects.
     */
    @GetMapping("/paging-and-sorting")
    public ResponseEntity<List<CategoryDto>> getAllEmployees(
            @RequestParam(defaultValue = "0") Integer pageNo,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(defaultValue = "categoryId") String sortBy) {
        log.info("Fetching paginated and sorted categories: page {}, size {}, sortBy {}", pageNo, pageSize, sortBy);
        List<CategoryDto> list = categoryService.getAllCategories(pageNo, pageSize, sortBy);
        return new ResponseEntity<>(list, new HttpHeaders(), HttpStatus.OK);
    }

    /**
     * Retrieves detailed information of a specific category by its ID.
     * 
     * @param categoryId The ID of the category to retrieve.
     * @return ResponseEntity containing the CategoryDto object of the specified category.
     */
    @GetMapping("/{categoryId}")
    public ResponseEntity<CategoryDto> findById(@PathVariable("categoryId")
                                                @NotBlank(message = "Category ID must not be blank")
                                                @Valid final String categoryId) {
        log.info("Fetching category by ID: {}", categoryId);
        return ResponseEntity.ok(categoryService.findById(Integer.parseInt(categoryId)));
    }

    /**
     * Creates a new category.
     * 
     * @param categoryDto The CategoryDto object containing the category details.
     * @return ResponseEntity containing the saved CategoryDto object.
     */
    @PostMapping
    public ResponseEntity<CategoryDto> save(@RequestBody @NotNull(message = "Category input must not be NULL")
                                            @Valid final CategoryDto categoryDto) {
        log.info("Saving new category");
        return ResponseEntity.ok(categoryService.save(categoryDto));
    }

    /**
     * Creates multiple categories in bulk.
     * 
     * @param categoryDtos List of CategoryDto objects to be saved.
     * @return ResponseEntity containing a list of saved CategoryDto objects.
     */
    @PostMapping("/bulk")
    public ResponseEntity<List<CategoryDto>> saveAll(@RequestBody List<CategoryDto> categoryDtos) {
        log.info("Saving multiple categories in bulk");
        List<CategoryDto> savedCategories = categoryService.saveAll(categoryDtos);
        return new ResponseEntity<>(savedCategories, HttpStatus.CREATED);
    }

    /**
     * Updates an existing category.
     * 
     * @param categoryDto The CategoryDto object containing the updated category details.
     * @return ResponseEntity containing the updated CategoryDto object.
     */
    @PutMapping
    public ResponseEntity<CategoryDto> update(@RequestBody
                                              @NotNull(message = "Category input must not be NULL")
                                              @Valid final CategoryDto categoryDto) {
        log.info("Updating category");
        return ResponseEntity.ok(categoryService.update(categoryDto));
    }

    /**
     * Updates a category by its ID.
     * 
     * @param categoryId The ID of the category to update.
     * @param categoryDto The CategoryDto object containing the updated category details.
     * @return ResponseEntity containing the updated CategoryDto object.
     */
    @PutMapping("/{categoryId}")
    public ResponseEntity<CategoryDto> update(@PathVariable("categoryId")
                                              @NotBlank(message = "Category ID must not be blank")
                                              @Valid final String categoryId,
                                              @RequestBody @NotNull(message = "Category input must not be NULL")
                                              @Valid final CategoryDto categoryDto) {
        log.info("Updating category with ID: {}", categoryId);
        return ResponseEntity.ok(categoryService.update(Integer.parseInt(categoryId), categoryDto));
    }

    /**
     * Deletes a category by its ID.
     * 
     * @param categoryId The ID of the category to delete.
     * @return ResponseEntity indicating whether the deletion was successful.
     */
    @DeleteMapping("/{categoryId}")
    public ResponseEntity<Boolean> deleteById(@PathVariable("categoryId") final String categoryId) {
        log.info("Deleting category by ID: {}", categoryId);
        categoryService.deleteById(Integer.parseInt(categoryId));
        return ResponseEntity.ok(true);
    }
}
