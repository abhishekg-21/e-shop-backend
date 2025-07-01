// src/main/java/com/eshop/e_shop_backend/dto/CartResponseDTO.java
package com.eshop.e_shop_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal; // IMPORTANT: Import BigDecimal
import java.util.List;

/**
 * DTO representing the response structure for a user's shopping cart.
 * Corrected totalAmount to BigDecimal and adjusted constructor parameters.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor // This constructor must match the one used in CartServiceImpl
public class CartResponseDTO {
    private String customerEmail;
    private List<CartItemDTO> items;
    private BigDecimal totalAmount; // CORRECTED: Changed from Double to BigDecimal
    private Integer totalQuantity; // Total quantity of ALL items (sum of quantities)
    private Integer totalUniqueItems; // Total count of UNIQUE product IDs in cart (number of entries in map)

    // The constructor in CartServiceImpl is passing:
    // new CartResponseDTO(userEmail, items, totalAmount, totalItems, totalItems);
    // So, this AllArgsConstructor will be used:
    // public CartResponseDTO(String customerEmail, List<CartItemDTO> items,
    // BigDecimal totalAmount, Integer totalQuantity, Integer totalUniqueItems) {
    // ... }
}
