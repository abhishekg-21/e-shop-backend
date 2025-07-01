// src/main/java/com/eshop/e_shop_backend/service/ProductService.java
package com.eshop.e_shop_backend.service;

import com.eshop.e_shop_backend.dto.ProductDTO;
import org.springframework.data.domain.Page; // Import Page
import org.springframework.data.domain.Pageable; // Import Pageable

import java.util.List;
import java.util.Optional;

/**
 * Service interface for managing product data.
 */
public interface ProductService {
    // MODIFIED: Returns Page<ProductDTO> and accepts Pageable
    Page<ProductDTO> getAllProducts(Pageable pageable);

    // CORRECTED: Takes String id, returns Optional<ProductDTO>
    Optional<ProductDTO> getProductById(String id);

    // CORRECTED: Takes ProductDTO, returns ProductDTO
    ProductDTO createProduct(ProductDTO productDTO);

    // CORRECTED: Takes String id and ProductDTO, returns ProductDTO
    ProductDTO updateProduct(String id, ProductDTO productDTO);

    // CORRECTED: Takes String id
    void deleteProduct(String id);
}