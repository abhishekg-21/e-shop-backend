// src/main/java/com/eshop/e_shop_backend/repository/ProductRepository.java
package com.eshop.e_shop_backend.repository;

import com.eshop.e_shop_backend.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, String> { // <<-- THIS MUST BE String
    Optional<Product> findByName(String name); // Example, if you have it
    // Add other custom query methods if needed
}
