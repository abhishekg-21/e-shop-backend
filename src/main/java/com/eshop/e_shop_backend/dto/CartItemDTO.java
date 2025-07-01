// src/main/java/com/eshop/e_shop_backend/dto/CartItemDTO.java
package com.eshop.e_shop_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO for representing a single item in the shopping cart.
 * Used for adding/updating items in the cart.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartItemDTO {
    private String productId;
    private Integer quantity;
    // You might add more fields here if needed for display or further processing,
    // e.g., productName, price, imageUrl, but for adding to cart, productId and
    // quantity are essential.
}
