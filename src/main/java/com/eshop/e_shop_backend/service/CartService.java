// src/main/java/com/eshop/e_shop_backend/service/CartService.java
package com.eshop.e_shop_backend.service;

import com.eshop.e_shop_backend.dto.CartItemDTO; // Assuming you'll use this for add/update
import com.eshop.e_shop_backend.model.Cart; // Assuming you have a Cart entity
import com.eshop.e_shop_backend.model.User; // To identify the user whose cart is being modified
import java.util.Optional;

public interface CartService {

    /**
     * Adds a product to the user's cart or updates its quantity if already present.
     * 
     * @param user        The authenticated user.
     * @param cartItemDTO The DTO containing productId and quantity.
     * @return The updated Cart entity.
     * @throws RuntimeException if product or user is not found.
     */
    Cart addProductToCart(User user, CartItemDTO cartItemDTO);

    /**
     * Retrieves the cart for a given user.
     * 
     * @param user The authenticated user.
     * @return An Optional containing the Cart if found.
     */
    Optional<Cart> getCartByUser(User user);

    // Add other cart operations as needed (e.g., update quantity, remove item,
    // clear cart)
    // For example:
    // Cart updateCartItemQuantity(User user, String productId, int quantity);
    // void removeCartItem(User user, String productId);
    // void clearCart(User user);
}
