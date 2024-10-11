package com.kirangajul.productservice.api;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kirangajul.productservice.dto.ProductDto;
import com.kirangajul.productservice.service.ProductService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * REST controller for managing products.
 * This class handles requests for CRUD operations on products.
 */
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/products")
@CrossOrigin
public class ProductController {

    @Autowired
    private final ProductService productService;

    /**
     * Retrieves a list of all products.
     * 
     * @return List of ProductDto objects representing all products.
     */
    @GetMapping
    public List<ProductDto> findAll() {
        log.info("Fetching all products");
        return productService.findAll();
    }

    /**
     * Retrieves detailed information of a specific product by its ID.
     * 
     * @param productId The ID of the product to retrieve.
     * @return ResponseEntity containing the ProductDto object for the specified product.
     */
    @GetMapping("/{productId}")
    public ResponseEntity<ProductDto> findById(@PathVariable("productId")
                                               @NotBlank(message = "Product ID must not be blank!")
                                               @Valid final String productId) {
        log.info("Fetching product by ID: {}", productId);
        return ResponseEntity.ok(productService.findById(Integer.parseInt(productId)));
    }

    /**
     * Creates a new product.
     * 
     * @param productDto The ProductDto object containing the product details.
     * @return ResponseEntity containing the saved ProductDto object.
     */
    @PostMapping
    public ResponseEntity<ProductDto> save(@RequestBody
                                           @NotNull(message = "Product must not be NULL!")
                                           @Valid final ProductDto productDto) {
        log.info("Saving new product");
        return ResponseEntity.ok(productService.save(productDto));
    }
    
    /**
     * Creates multiple products in bulk.
     * 
     * @param productDtos List of ProductDto objects to be saved.
     * @return ResponseEntity containing the list of saved ProductDto objects.
     */
    @PostMapping("/bulk")
    public ResponseEntity<List<ProductDto>> saveAll(@RequestBody List<ProductDto> productDtos) {
        List<ProductDto> savedProducts = productService.saveAll(productDtos);
        log.info("Saving multiple products in bulk");
        return new ResponseEntity<>(savedProducts, HttpStatus.CREATED);
    }

    /**
     * Updates information for an existing product.
     * 
     * @param productDto The ProductDto object containing updated product details.
     * @return ResponseEntity containing the updated ProductDto object.
     */
    @PatchMapping
    public ResponseEntity<ProductDto> update(@RequestBody
                                             @NotNull(message = "Product must not be NULL!")
                                             @Valid final ProductDto productDto) {
        log.info("Updating product");
        return ResponseEntity.ok(productService.update(productDto));
    }

    /**
     * Updates a product by its ID.
     * 
     * @param productId The ID of the product to update.
     * @param productDto The ProductDto object containing updated product details.
     * @return ResponseEntity containing the updated ProductDto object.
     */
    @PutMapping("/{productId}")
    public ResponseEntity<ProductDto> update(@PathVariable("productId")
                                             @NotBlank(message = "Product ID must not be blank!")
                                             @Valid final String productId,
                                             @RequestBody
                                             @NotNull(message = "Product must not be NULL!")
                                             @Valid final ProductDto productDto) {
        log.info("Updating product with ID: {}", productId);
        return ResponseEntity.ok(productService.update(Integer.parseInt(productId), productDto));
    }

    /**
     * Deletes a product by its ID.
     * 
     * @param productId The ID of the product to delete.
     * @return ResponseEntity indicating whether the deletion was successful.
     */
    @DeleteMapping("/{productId}")
    public ResponseEntity<Boolean> deleteById(@PathVariable("productId") final String productId) {
        log.info("Deleting product by ID: {}", productId);
        productService.deleteById(Integer.parseInt(productId));
        return ResponseEntity.ok(true);
    }

}
