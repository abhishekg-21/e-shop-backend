// src/main/java/com/eshop/dto/OrderRequest.java
package com.eshop.e_shop_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * DTO for placing a new order.
 * Contains a list of items to be ordered.
 * Additional fields like shipping address, payment details could be added here.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderRequest {
    private List<OrderItemRequest> items; // List of products and quantities to include in the order
    // Optional: Add fields for shipping address, payment method, etc.
    // private String shippingAddress;
    // private String paymentMethod;
}