// src/main/java/com/eshop/e_shop_backend/controller/OrderController.java
package com.eshop.e_shop_backend.controller;

import com.eshop.e_shop_backend.dto.OrderRequest;
import com.eshop.e_shop_backend.dto.OrderResponse;
import com.eshop.e_shop_backend.dto.OrderStatusUpdate; // NEW: Import this DTO
import com.eshop.e_shop_backend.model.Order;
import com.eshop.e_shop_backend.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional; // NEW: Import Optional
import java.util.stream.Collectors;

/**
 * REST Controller for managing orders.
 * Provides endpoints for placing orders (users) and managing orders (admins).
 */
@RestController
@RequestMapping("/api") // Base path for order endpoints (e.g., /api/orders, /api/admin/orders)
public class OrderController {

    private final OrderService orderService; // Inject the OrderService

    // Constructor injection for dependencies
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    /**
     * Handles placing a new order. Accessible by authenticated users (ROLE_USER or
     * ROLE_ADMIN).
     * The order is linked to the currently authenticated user.
     *
     * @param orderRequest DTO containing details of the items to be ordered.
     * @return ResponseEntity with OrderResponse DTO of the newly placed order (HTTP
     *         201 Created).
     */
    @PreAuthorize("isAuthenticated()") // User must be logged in to place an order
    @PostMapping("/orders")
    public ResponseEntity<OrderResponse> placeOrder(@RequestBody OrderRequest orderRequest) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userEmail = ((UserDetails) authentication.getPrincipal()).getUsername(); // Get email of placing user

        Order placedOrder = orderService.placeOrder(userEmail, orderRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(new OrderResponse(placedOrder));
    }

    /**
     * Retrieves all orders in the system. Accessible only by ADMINs.
     *
     * @return ResponseEntity with a list of OrderResponse DTOs (HTTP 200 OK).
     */
    @PreAuthorize("hasRole('ADMIN')") // Only ADMINs can view all orders
    @GetMapping("/admin/orders")
    public ResponseEntity<List<OrderResponse>> getAllOrders() {
        List<Order> orders = orderService.getAllOrders();
        List<OrderResponse> orderDTOs = orders.stream()
                .map(OrderResponse::new) // Convert Order entities to OrderResponse DTOs
                .collect(Collectors.toList());
        return ResponseEntity.ok(orderDTOs);
    }

    /**
     * Retrieves orders for the currently authenticated user. Accessible by any
     * authenticated user.
     *
     * @return ResponseEntity with a list of OrderResponse DTOs belonging to the
     *         current user (HTTP 200 OK).
     */
    @PreAuthorize("isAuthenticated()") // Only authenticated users can view their own orders
    @GetMapping("/orders/my")
    public ResponseEntity<List<OrderResponse>> getMyOrders() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userEmail = ((UserDetails) authentication.getPrincipal()).getUsername();
        List<Order> userOrders = orderService.getOrdersForUser(userEmail);
        List<OrderResponse> orderDTOs = userOrders.stream()
                .map(OrderResponse::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(orderDTOs);
    }

    /**
     * Retrieves a single order by its ID.
     * Accessible by ADMINs (any order) or by the user who placed that specific
     * order.
     * Uses SpEL for authorization check: 'hasRole('ADMIN')' OR
     * '@orderService.isOrderOwnedByUser(#id, authentication.principal.username)'.
     *
     * @param id The ID of the order to retrieve (now String for UUID).
     * @return ResponseEntity with OrderResponse DTO (HTTP 200 OK), or not found
     *         (HTTP 404).
     */
    @PreAuthorize("hasRole('ADMIN') or @orderService.isOrderOwnedByUser(#id, authentication.principal.username)")
    @GetMapping("/orders/{id}")
    public ResponseEntity<OrderResponse> getOrderById(@PathVariable String id) { // CHANGED: Type to String
        Optional<Order> orderOpt = orderService.getOrderById(id); // CHANGED: Returns Optional
        if (orderOpt.isEmpty()) { // CHANGED: Check if Optional is empty
            return ResponseEntity.notFound().build(); // Return 404 if order not found
        }
        return ResponseEntity.ok(new OrderResponse(orderOpt.get())); // CHANGED: Get value from Optional
    }

    /**
     * Updates the status of an order. Accessible only by ADMINs.
     *
     * @param id           The ID of the order to update (now String for UUID).
     * @param statusUpdate DTO containing the new status string (e.g., "PROCESSING",
     *                     "SHIPPED").
     * @return ResponseEntity with OrderResponse DTO of the updated order (HTTP 200
     *         OK), or not found (HTTP 404).
     */
    @PreAuthorize("hasRole('ADMIN')") // Only ADMINs can update order status
    @PutMapping("/admin/orders/{id}/status")
    public ResponseEntity<OrderResponse> updateOrderStatus(@PathVariable String id, // CHANGED: Type to String
            @RequestBody OrderStatusUpdate statusUpdate) {
        Order updatedOrder = orderService.updateOrderStatus(id, statusUpdate.getNewStatus());
        if (updatedOrder == null) {
            return ResponseEntity.notFound().build(); // Return 404 if order not found
        }
        return ResponseEntity.ok(new OrderResponse(updatedOrder));
    }
}
