// src/main/java/com/eshop/e_shop_backend/dto/OrderItemRequest.java
package com.eshop.e_shop_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO for requesting an item to be added to an order.
 * Corrected productId to String for UUID compatibility.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderItemRequest {
    private String productId; // CORRECTED: This MUST be String
    private Integer quantity;
}
