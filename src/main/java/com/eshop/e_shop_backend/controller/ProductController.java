// src/main/java/com/eshop/e_shop_backend/controller/ProductController.java
package com.eshop.e_shop_backend.controller;

import com.eshop.e_shop_backend.dto.ProductDTO;
import com.eshop.e_shop_backend.service.ProductService;
import org.springframework.data.domain.Page; // Import Page
import org.springframework.data.domain.Pageable; // Import Pageable
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * REST Controller for managing products.
 * Provides public endpoints for viewing products and admin-only endpoints for
 * CRUD operations.
 */
@RestController
@RequestMapping("/api")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    /**
     * Retrieves a list of all products with pagination. Accessible to all users
     * (public).
     * Spring Data automatically resolves Pageable from request parameters (page,
     * size, sort).
     *
     * @param pageable Pagination and sorting information.
     * @return ResponseEntity with a Page of ProductDTOs (HTTP 200 OK).
     */
    @GetMapping("/products")
    public ResponseEntity<Page<ProductDTO>> getAllProducts(Pageable pageable) { // MODIFIED: Accepts Pageable, returns
                                                                                // Page
        Page<ProductDTO> productDTOs = productService.getAllProducts(pageable);
        return ResponseEntity.ok(productDTOs);
    }

    @GetMapping("/products/{id}")
    public ResponseEntity<ProductDTO> getProductById(@PathVariable String id) {
        Optional<ProductDTO> productDTOOptional = productService.getProductById(id);
        if (productDTOOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(productDTOOptional.get());
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/admin/products")
    public ResponseEntity<ProductDTO> createProduct(@RequestBody ProductDTO productDTO) {
        ProductDTO createdProductDTO = productService.createProduct(productDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdProductDTO);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/admin/products/{id}")
    public ResponseEntity<ProductDTO> updateProduct(@PathVariable String id, @RequestBody ProductDTO productDTO) {
        ProductDTO updatedProductDTO = productService.updateProduct(id, productDTO);
        if (updatedProductDTO == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updatedProductDTO);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/admin/products/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable String id) {
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }
}
