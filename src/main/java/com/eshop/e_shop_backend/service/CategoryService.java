// src/main/java/com/eshop/e_shop_backend/service/CategoryService.java
package com.eshop.e_shop_backend.service;

import com.eshop.e_shop_backend.model.Category; // Assuming we'll return Category entities directly for simplicity
import java.util.List;
import java.util.Optional;

/**
 * Service interface for managing product categories.
 */
public interface CategoryService {
    List<Category> getAllCategories();

    Optional<Category> getCategoryById(String id);

    Category createCategory(Category category);

    Category updateCategory(String id, Category category);

    void deleteCategory(String id);
}