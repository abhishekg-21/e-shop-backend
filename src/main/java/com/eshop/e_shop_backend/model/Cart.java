// src/main/java/com/eshop/e_shop_backend/model/Cart.java
package com.eshop.e_shop_backend.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.EqualsAndHashCode; // Import this
import org.hibernate.annotations.GenericGenerator;

import java.util.HashSet;
import java.util.Set;

/**
 * Represents a shopping cart, which belongs to a single user.
 * Contains a collection of CartItem entities.
 */
@Entity
@Table(name = "carts")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false) // Add this
public class Cart {
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", updatable = false, nullable = false, columnDefinition = "VARCHAR(255)")
    private String id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id", unique = true)
    @EqualsAndHashCode.Exclude // <--- EXCLUDE USER FROM EQUALS/HASHCODE
    private User user;

    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @EqualsAndHashCode.Exclude // <--- EXCLUDE CARTITEMS FROM EQUALS/HASHCODE
    private Set<CartItem> cartItems = new HashSet<>();

    // Helper method to add an item to the cart (ensures bi-directional
    // relationship)
    public void addCartItem(CartItem item) {
        this.cartItems.add(item);
        item.setCart(this);
    }

    // Helper method to remove an item from the cart
    public void removeCartItem(CartItem item) {
        this.cartItems.remove(item);
        item.setCart(null);
    }
}
