// src/main/java/com/eshop/e_shop_backend/controller/CategoryController.java
package com.eshop.e_shop_backend.controller;

import com.eshop.e_shop_backend.model.Category;
import com.eshop.e_shop_backend.service.CategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize; // Keep if you plan to secure category endpoints
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * REST Controller for managing product categories.
 * Provides public endpoints for viewing categories and admin-only endpoints for
 * CRUD operations.
 */
@RestController
@RequestMapping("/api/categories") // Base path for category endpoints
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    /**
     * Retrieves a list of all categories. Accessible to all users (public).
     * This endpoint is now explicitly permitted in SecurityConfig.
     *
     * @return ResponseEntity with a list of Category entities (HTTP 200 OK).
     */
    @GetMapping // Maps to /api/categories
    public ResponseEntity<List<Category>> getAllCategories() {
        List<Category> categories = categoryService.getAllCategories();
        return ResponseEntity.ok(categories);
    }

    /**
     * Retrieves a single category by its ID. Accessible to all users (public).
     *
     * @param id The ID of the category to retrieve.
     * @return ResponseEntity with Category (HTTP 200 OK), or not found (HTTP 404).
     */
    @GetMapping("/{id}") // Maps to /api/categories/{id}
    public ResponseEntity<Category> getCategoryById(@PathVariable String id) {
        Optional<Category> category = categoryService.getCategoryById(id);
        return category.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // --- Admin-only endpoints for Categories (example, can be added later if
    // needed) ---
    // @PreAuthorize("hasRole('ADMIN')")
    // @PostMapping
    // public ResponseEntity<Category> createCategory(@RequestBody Category
    // category) {
    // Category createdCategory = categoryService.createCategory(category);
    // return ResponseEntity.status(HttpStatus.CREATED).body(createdCategory);
    // }

    // @PreAuthorize("hasRole('ADMIN')")
    // @PutMapping("/{id}")
    // public ResponseEntity<Category> updateCategory(@PathVariable String id,
    // @RequestBody Category category) {
    // Category updatedCategory = categoryService.updateCategory(id, category);
    // if (updatedCategory == null) {
    // return ResponseEntity.notFound().build();
    // }
    // return ResponseEntity.ok(updatedCategory);
    // }

    // @PreAuthorize("hasRole('ADMIN')")
    // @DeleteMapping("/{id}")
    // public ResponseEntity<Void> deleteCategory(@PathVariable String id) {
    // categoryService.deleteCategory(id);
    // return ResponseEntity.noContent().build();
    // }
}