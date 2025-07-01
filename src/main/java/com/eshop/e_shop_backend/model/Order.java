// src/main/java/com/eshop/e_shop_backend/model/Order.java
package com.eshop.e_shop_backend.model;

import jakarta.persistence.*;
import lombok.Data; // Make sure Lombok is imported and working
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator; // For UUID generation if using String ID

import java.math.BigDecimal; // Import for BigDecimal
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "orders") // Ensure your table name is correct
@Data // Lombok: This annotation automatically generates getters, setters, equals,
      // hashCode, and toString for ALL fields
@NoArgsConstructor // Lombok: Generates no-argument constructor
public class Order {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", columnDefinition = "VARCHAR(255)") // For String/UUID ID, ensure consistency
    private String id; // This MUST be String if your repository and service expect String IDs

    @ManyToOne(fetch = FetchType.LAZY) // Many orders to one user
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    private LocalDateTime orderDate;

    // --- CRITICAL: Ensure these fields are present and correctly typed ---
    @Column(nullable = false)
    private String orderStatus; // Changed to String to match OrderServiceImpl's usage
                                // If you want to store it as an Enum directly, change type to OrderStatus
                                // and annotate with @Enumerated(EnumType.STRING)

    @Column(nullable = false, precision = 19, scale = 2) // Precision and scale for currency
    private BigDecimal totalAmount; // This MUST be BigDecimal
    // --- END CRITICAL FIELDS ---

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private Set<OrderItem> orderItems = new HashSet<>();

    // Helper method to add an order item and maintain bidirectional link
    public void addOrderItem(OrderItem item) {
        orderItems.add(item);
        item.setOrder(this);
    }

    // Helper method to remove an order item
    public void removeOrderItem(OrderItem item) {
        orderItems.remove(item);
        item.setOrder(null);
    }
}
