// src/main/java/com/eshop/e_shop_backend/repository/CategoryRepository.java
package com.eshop.e_shop_backend.repository;

import com.eshop.e_shop_backend.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, String> {
    // Method to find a category by its name, used in ProductServiceImpl
    Optional<Category> findByName(String name);
}
