// src/main/java/com/eshop/dto/OrderStatusUpdate.java
package com.eshop.e_shop_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO for updating the status of an order.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderStatusUpdate {
    private String newStatus; // The new status as a string (e.g., "PROCESSING", "SHIPPED")
}