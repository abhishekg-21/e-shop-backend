// src/main/java/com/eshop/repository/OrderItemRepository.java
package com.eshop.e_shop_backend.repository;

import com.eshop.e_shop_backend.model.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the OrderItem entity.
 * Provides standard CRUD operations.
 */
@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
    // Typically, OrderItem entities are managed via their parent Order entity
    // (cascading).
    // You might not need many custom queries here unless you need to fetch order
    // items
    // independently of their parent order.
}