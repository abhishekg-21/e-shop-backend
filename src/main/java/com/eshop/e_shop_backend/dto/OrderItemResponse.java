// src/main/java/com/eshop/e_shop_backend/dto/OrderItemResponse.java
package com.eshop.e_shop_backend.dto;

import com.eshop.e_shop_backend.model.OrderItem; // Import OrderItem entity for conversion
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal; // IMPORTANT: Import BigDecimal

/**
 * DTO representing a single item that is part of an existing order.
 * Corrected types for productId and priceAtPurchase to match entity
 * definitions.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderItemResponse {
    private String productId; // CORRECTED: Changed from Long to String (to match Product entity ID)
    private String productName;
    private Integer quantity;
    private BigDecimal priceAtPurchase; // CORRECTED: Changed from Double to BigDecimal (to match OrderItem entity)
    private String imageUrl; // Optional, for displaying product image in order details

    /**
     * Constructor to convert an OrderItem entity to OrderItemResponse DTO.
     *
     * @param orderItem The OrderItem entity to convert.
     */
    public OrderItemResponse(OrderItem orderItem) {
        this.productId = orderItem.getProduct().getId(); // Now correctly assigns String ID
        this.productName = orderItem.getProduct().getName();
        this.quantity = orderItem.getQuantity();
        this.priceAtPurchase = orderItem.getPriceAtPurchase(); // Now correctly assigns BigDecimal
        this.imageUrl = orderItem.getProduct().getImageUrl();
    }
}
