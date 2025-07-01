// src/main/java/com/eshop/e_shop_backend/repository/OrderRepository.java
package com.eshop.e_shop_backend.repository;

import com.eshop.e_shop_backend.model.Order;
import com.eshop.e_shop_backend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional; // Import for Optional

/**
 * Spring Data JPA repository for the Order entity.
 * Provides standard CRUD operations and custom query methods.
 * IMPORTANT: Assuming Order ID is a String (UUID) for consistency with
 * frontend.
 * If your Order ID is a Long, please change JpaRepository<Order, String> to
 * JpaRepository<Order, Long>.
 */
@Repository
public interface OrderRepository extends JpaRepository<Order, String> { // Changed from Long to String

    /**
     * Finds all Orders placed by a specific User.
     *
     * @param user The User entity whose orders are to be retrieved.
     * @return A list of Orders placed by the given user.
     */
    List<Order> findByUser(User user);

    /**
     * Counts the number of orders with a specific status.
     *
     * @param orderStatus The status of the orders to count (e.g., "PENDING",
     *                    "DELIVERED").
     * @return The count of orders matching the given status.
     *         NOTE: This assumes your Order entity has a field named 'orderStatus'
     *         If your field is just 'status', rename this method to countByStatus.
     */
    long countByOrderStatus(String orderStatus);

    /**
     * Calculates the sum of totalAmount for all orders with a specific status.
     *
     * @param status The status of the orders to sum (e.g., "DELIVERED").
     * @return An Optional containing the sum of total amounts, or empty if no
     *         orders match.
     *         NOTE: This assumes your Order entity has a field named 'orderStatus'.
     *         If your field is just 'status', adjust the @Query accordingly.
     */
    @Query("SELECT SUM(o.totalAmount) FROM Order o WHERE o.orderStatus = :status")
    Optional<BigDecimal> sumTotalAmountByOrderStatus(@Param("status") String status);
}
