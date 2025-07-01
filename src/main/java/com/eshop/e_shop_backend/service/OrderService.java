// src/main/java/com/eshop/e_shop_backend/service/OrderService.java
package com.eshop.e_shop_backend.service;

import com.eshop.e_shop_backend.dto.OrderRequest;
import com.eshop.e_shop_backend.model.Order;

import java.util.List;
import java.util.Optional; // IMPORTANT: Import Optional

/**
 * Service interface for managing orders.
 */
public interface OrderService {
    Order placeOrder(String userEmail, OrderRequest orderRequest);

    List<Order> getAllOrders(); // For admin

    List<Order> getOrdersForUser(String userEmail); // For regular user to see their orders

    // THIS IS THE CRITICAL LINE FOR getOrderById: MUST return Optional<Order>
    Optional<Order> getOrderById(String id); // For admin or user's own order

    // This method signature is already correct (Order return type, String id)
    Order updateOrderStatus(String id, String newStatus); // For admin

    // This method signature is already correct (boolean return type, String
    // orderId)
    boolean isOrderOwnedByUser(String orderId, String username); // Helper for security checks
}
