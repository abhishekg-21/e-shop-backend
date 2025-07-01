// src/main/java/com/eshop/e_shop_backend/service/CategoryServiceImpl.java
package com.eshop.e_shop_backend.service;

import com.eshop.e_shop_backend.model.Category;
import com.eshop.e_shop_backend.repository.CategoryRepository;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional; // Ensure this import is correct

import java.util.List;
import java.util.Optional;

/**
 * Implementation of CategoryService for managing category data.
 */
@Service
@Transactional
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public Optional<Category> getCategoryById(String id) {
        return categoryRepository.findById(id);
    }

    @Override
    public Category createCategory(Category category) {
        return categoryRepository.save(category);
    }

    @Override
    public Category updateCategory(String id, Category categoryDetails) {
        Optional<Category> existingCategoryOpt = categoryRepository.findById(id);
        if (existingCategoryOpt.isEmpty()) {
            return null; // Or throw a specific exception for not found
        }
        Category existingCategory = existingCategoryOpt.get();
        existingCategory.setName(categoryDetails.getName());
        return categoryRepository.save(existingCategory);
    }

    @Override
    public void deleteCategory(String id) {
        categoryRepository.deleteById(id);
    }
}