// src/main/java/com/eshop/e_shop_backend/dto/ProductDTO.java
package com.eshop.e_shop_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.eshop.e_shop_backend.model.Product; // Import the Product entity
import java.math.BigDecimal; // IMPORTANT: Import BigDecimal

/**
 * DTO for product data.
 * Used for creating, updating, and retrieving product information.
 * Corrected types for ID and price, and handles category name extraction.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDTO {
    private String id; // CORRECTED: Changed from Long to String (to match Product entity ID)
    private String name;
    private String description;
    private BigDecimal price; // CORRECTED: Changed from Double to BigDecimal (to match Product entity)
    private Integer stockQuantity;
    private String imageUrl;
    private String category; // This represents the category name (String)

    /**
     * Constructor to convert a Product entity to a ProductDTO.
     *
     * @param product The Product entity to convert.
     */
    public ProductDTO(Product product) {
        this.id = product.getId(); // Now correctly assigns String ID
        this.name = product.getName();
        this.description = product.getDescription();
        this.price = product.getPrice(); // Now correctly assigns BigDecimal
        this.stockQuantity = product.getStockQuantity();
        this.imageUrl = product.getImageUrl();
        // CORRECTED: Get the name from the Category object
        this.category = product.getCategory() != null ? product.getCategory().getName() : null;
    }
}
