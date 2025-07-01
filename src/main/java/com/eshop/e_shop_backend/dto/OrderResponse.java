// src/main/java/com/eshop/e_shop_backend/dto/OrderResponse.java
package com.eshop.e_shop_backend.dto;

import com.eshop.e_shop_backend.model.Order; // Import Order entity for conversion
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.math.BigDecimal; // IMPORTANT: Import BigDecimal
import java.util.stream.Collectors;

/**
 * DTO representing the response structure for an existing order.
 * Corrected types for ID, status, and totalAmount to match Order entity.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderResponse {
    private String id; // CORRECTED: Changed from Long to String (to match Order entity ID)
    private String customerEmail;
    private LocalDateTime orderDate;
    private String status; // CORRECTED: Remains String, but will now map from order.getOrderStatus()
    private BigDecimal totalAmount; // CORRECTED: Changed from Double to BigDecimal (to match Order entity)
    private List<OrderItemResponse> items; // List of ordered items

    /**
     * Constructor to convert an Order entity to OrderResponse DTO.
     *
     * @param order The Order entity to convert.
     */
    public OrderResponse(Order order) {
        this.id = order.getId(); // Now correctly assigns String ID
        this.customerEmail = order.getUser().getEmail(); // Assuming user is eagerly loaded or fetched
        this.orderDate = order.getOrderDate();
        this.status = order.getOrderStatus(); // CORRECTED: Use getOrderStatus() as it's a String field in Order entity
        this.totalAmount = order.getTotalAmount(); // Now correctly assigns BigDecimal
        this.items = order.getOrderItems().stream()
                .map(OrderItemResponse::new)
                .collect(Collectors.toList()); // Convert OrderItem entities to OrderItemResponse DTOs
    }
}
