// src/main/java/com/eshop/e_shop_backend/service/CartServiceImpl.java
package com.eshop.e_shop_backend.service;

import com.eshop.e_shop_backend.dto.CartItemDTO;
import com.eshop.e_shop_backend.model.Cart;
import com.eshop.e_shop_backend.model.CartItem;
import com.eshop.e_shop_backend.model.Product;
import com.eshop.e_shop_backend.model.User;
import com.eshop.e_shop_backend.repository.CartRepository;
import com.eshop.e_shop_backend.repository.ProductRepository;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;
import java.util.Set;

@Service
@Transactional
public class CartServiceImpl implements CartService {

    private static final Logger logger = LoggerFactory.getLogger(CartServiceImpl.class);

    private final CartRepository cartRepository;
    private final ProductRepository productRepository;

    public CartServiceImpl(CartRepository cartRepository, ProductRepository productRepository) {
        this.cartRepository = cartRepository;
        this.productRepository = productRepository;
    }

    @Override
    public Cart addProductToCart(User user, CartItemDTO cartItemDTO) {
        logger.info("Attempting to add product ID {} with quantity {} to cart for user {}",
                cartItemDTO.getProductId(), cartItemDTO.getQuantity(), user.getEmail());

        // Find the user's cart or create a new one if it doesn't exist
        Cart cart = cartRepository.findByUser(user)
                .orElseGet(() -> {
                    logger.info("Creating new cart for user {}", user.getEmail());
                    Cart newCart = new Cart();
                    newCart.setUser(user);
                    return newCart;
                });

        // Find the product
        Product product = productRepository.findById(cartItemDTO.getProductId())
                .orElseThrow(() -> {
                    logger.error("Product not found with ID: {}", cartItemDTO.getProductId());
                    return new RuntimeException("Product not found with ID: " + cartItemDTO.getProductId());
                });

        // Check if the product is already in the cart
        Optional<CartItem> existingCartItem = cart.getCartItems().stream()
                .filter(item -> item.getProduct().getId().equals(product.getId()))
                .findFirst();

        if (existingCartItem.isPresent()) {
            // Update quantity if item already exists
            CartItem item = existingCartItem.get();
            int newQuantity = item.getQuantity() + cartItemDTO.getQuantity();
            if (newQuantity > product.getStockQuantity()) {
                logger.warn("Attempted to add more than available stock for product {}. Requested: {}, Available: {}",
                        product.getName(), newQuantity, product.getStockQuantity());
                throw new RuntimeException("Cannot add more than available stock for " + product.getName()
                        + ". Available: " + product.getStockQuantity());
            }
            item.setQuantity(newQuantity);
            logger.info("Updated quantity for product {} in cart to {}", product.getName(), item.getQuantity());
        } else {
            // Add new item to cart
            if (cartItemDTO.getQuantity() > product.getStockQuantity()) {
                logger.warn("Attempted to add more than available stock for product {}. Requested: {}, Available: {}",
                        product.getName(), cartItemDTO.getQuantity(), product.getStockQuantity());
                throw new RuntimeException("Cannot add more than available stock for " + product.getName()
                        + ". Available: " + product.getStockQuantity());
            }
            CartItem newItem = new CartItem();
            newItem.setProduct(product);
            newItem.setQuantity(cartItemDTO.getQuantity());
            newItem.setCart(cart); // Set the back-reference to the cart
            cart.getCartItems().add(newItem); // Add to the set of cart items
            logger.info("Added new product {} to cart with quantity {}", product.getName(), newItem.getQuantity());
        }

        return cartRepository.save(cart); // Save the updated cart
    }

    @Override
    public Optional<Cart> getCartByUser(User user) {
        logger.info("Fetching cart for user: {}", user.getEmail());
        return cartRepository.findByUser(user);
    }

    // You can add more methods here for updating quantity, removing items, clearing
    // cart etc.
    // For example:
    // @Override
    // public Cart updateCartItemQuantity(User user, String productId, int quantity)
    // {
    // // Implementation for updating quantity
    // }
    // @Override
    // public void removeCartItem(User user, String productId) {
    // // Implementation for removing item
    // }
}
