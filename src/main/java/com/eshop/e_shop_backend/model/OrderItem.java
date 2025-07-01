// src/main/java/com/eshop/e_shop_backend/model/OrderItem.java
package com.eshop.e_shop_backend.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import java.math.BigDecimal; // IMPORT THIS!

@Entity
@Table(name = "order_items") // Ensure your table name is correct
@Data // Lombok: Generates getters, setters, etc.
@NoArgsConstructor // Lombok: Generates no-argument constructor
public class OrderItem {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", columnDefinition = "VARCHAR(255)")
    private String id; // Assuming String/UUID for OrderItem IDs

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @Column(nullable = false)
    private Integer quantity;

    // --- CRITICAL: Ensure this field is present and correctly typed ---
    @Column(nullable = false, precision = 19, scale = 2) // Set precision and scale for currency
    private BigDecimal priceAtPurchase; // <--- THIS MUST BE BigDecimal
    // --- END CRITICAL FIELD ---

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    private Order order; // Link back to the Order
}
