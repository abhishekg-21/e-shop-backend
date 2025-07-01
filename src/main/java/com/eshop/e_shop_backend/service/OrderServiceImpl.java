// src/main/java/com/eshop/e_shop_backend/service/OrderServiceImpl.java
package com.eshop.e_shop_backend.service;

import com.eshop.e_shop_backend.dto.OrderItemRequest;
import com.eshop.e_shop_backend.dto.OrderRequest;
import com.eshop.e_shop_backend.model.Order;
import com.eshop.e_shop_backend.model.OrderItem;
import com.eshop.e_shop_backend.model.OrderStatus; // Assuming OrderStatus is an enum
import com.eshop.e_shop_backend.model.Product;
import com.eshop.e_shop_backend.model.User;
import com.eshop.e_shop_backend.repository.OrderItemRepository;
import com.eshop.e_shop_backend.repository.OrderRepository;
import com.eshop.e_shop_backend.repository.ProductRepository;
import com.eshop.e_shop_backend.repository.UserRepository;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;

import java.math.BigDecimal; // Import for BigDecimal
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Implementation of OrderService for managing orders.
 */
@Service("orderService") // Name the bean for use in @PreAuthorize expressions
@Transactional // Ensures methods run within a transaction
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final OrderItemRepository orderItemRepository;

    // Constructor injection
    public OrderServiceImpl(OrderRepository orderRepository,
            UserRepository userRepository,
            ProductRepository productRepository,
            OrderItemRepository orderItemRepository) {
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
        this.productRepository = productRepository;
        this.orderItemRepository = orderItemRepository;
    }

    /**
     * Places a new order for a given user.
     * Manages product stock and creates order items.
     *
     * @param userEmail    The email of the user placing the order.
     * @param orderRequest DTO containing details of the items to order.
     * @return The newly placed Order entity.
     * @throws RuntimeException if user/product not found or insufficient stock.
     */
    @Override
    public Order placeOrder(String userEmail, OrderRequest orderRequest) {
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("User not found: " + userEmail));

        Order order = new Order();
        order.setUser(user);
        order.setOrderDate(LocalDateTime.now());
        order.setOrderStatus(OrderStatus.PENDING.toString()); // Assuming status is stored as String
        order.setTotalAmount(BigDecimal.ZERO); // Initialize with BigDecimal.ZERO

        BigDecimal calculatedTotalAmount = BigDecimal.ZERO; // Use BigDecimal for calculation
        for (OrderItemRequest itemRequest : orderRequest.getItems()) {
            Product product = productRepository.findById(itemRequest.getProductId()) // Product ID should be String/UUID
                    .orElseThrow(
                            () -> new RuntimeException("Product not found with ID: " + itemRequest.getProductId()));

            if (product.getStockQuantity() < itemRequest.getQuantity()) {
                throw new RuntimeException("Not enough stock for product: " + product.getName() + ". Available: "
                        + product.getStockQuantity() + ", Requested: " + itemRequest.getQuantity());
            }

            // Decrease product stock
            product.setStockQuantity(product.getStockQuantity() - itemRequest.getQuantity());
            productRepository.save(product); // Save updated product stock

            OrderItem orderItem = new OrderItem();
            orderItem.setProduct(product);
            orderItem.setQuantity(itemRequest.getQuantity());
            orderItem.setPriceAtPurchase(product.getPrice()); // Record price at time of purchase

            order.addOrderItem(orderItem); // Add to order's collection (handles bidirectional link)

            // Calculate total using BigDecimal
            calculatedTotalAmount = calculatedTotalAmount.add(
                    product.getPrice().multiply(BigDecimal.valueOf(itemRequest.getQuantity())));
        }

        order.setTotalAmount(calculatedTotalAmount);
        return orderRepository.save(order); // Save the new order and its items (due to cascade)
    }

    /**
     * Retrieves all orders (typically for administrative view).
     *
     * @return A list of all Order entities.
     */
    @Override
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    /**
     * Retrieves all orders for a specific user.
     *
     * @param userEmail The email of the user whose orders are to be retrieved.
     * @return A list of Order entities for the specified user.
     * @throws RuntimeException if user not found.
     */
    @Override
    public List<Order> getOrdersForUser(String userEmail) {
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("User not found: " + userEmail));
        return orderRepository.findByUser(user);
    }

    /**
     * Retrieves a single order by its ID.
     *
     * @param id The ID of the order to retrieve (String).
     * @return An Optional containing the Order entity if found, empty otherwise.
     */
    @Override
    public Optional<Order> getOrderById(String id) { // CORRECTED: Return type is Optional<Order>
        return orderRepository.findById(id); // Directly return the Optional
    }

    /**
     * Updates the status of an existing order.
     *
     * @param id        The ID of the order to update (String).
     * @param newStatus The new status as a string (e.g., "SHIPPED").
     * @return The updated Order entity, or null if order not found.
     * @throws IllegalArgumentException if the newStatus string is not a valid
     *                                  OrderStatus enum.
     */
    @Override
    public Order updateOrderStatus(String id, String newStatus) { // Matches interface
        Optional<Order> orderOpt = orderRepository.findById(id);
        if (orderOpt.isEmpty()) {
            return null; // Or throw a specific exception
        }
        Order order = orderOpt.get();
        try {
            // Assuming orderStatus field in Order entity is a String, store enum name
            order.setOrderStatus(OrderStatus.valueOf(newStatus.toUpperCase()).toString());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid order status provided: " + newStatus, e);
        }
        return orderRepository.save(order);
    }

    /**
     * Helper method for Spring Security's @PreAuthorize to check if an order
     * belongs to a user.
     *
     * @param orderId  The ID of the order (String).
     * @param username The username (email) of the user.
     * @return True if the order is owned by the user, false otherwise.
     */
    @Override
    public boolean isOrderOwnedByUser(String orderId, String username) { // Matches interface
        return orderRepository.findById(orderId)
                .map(order -> order.getUser().getEmail().equals(username))
                .orElse(false);
    }
}
