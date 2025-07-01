// src/main/java/com/eshop/e_shop_backend/controller/CartController.java
package com.eshop.e_shop_backend.controller;

import com.eshop.e_shop_backend.dto.CartItemDTO;
import com.eshop.e_shop_backend.model.Cart;
import com.eshop.e_shop_backend.model.User;
import com.eshop.e_shop_backend.service.CartService;
import com.eshop.e_shop_backend.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional; // <--- ADD THIS IMPORT STATEMENT

@RestController
@RequestMapping("/api/cart")
public class CartController {

    private static final Logger logger = LoggerFactory.getLogger(CartController.class);

    private final CartService cartService;
    private final UserService userService;

    public CartController(CartService cartService, UserService userService) {
        this.cartService = cartService;
        this.userService = userService;
    }

    @PostMapping("/add")
    public ResponseEntity<?> addProductToCart(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestBody CartItemDTO cartItemDTO) {
        try {
            logger.info("Received request to add product ID: {} with quantity: {} to cart for user: {}",
                    cartItemDTO.getProductId(), cartItemDTO.getQuantity(), userDetails.getUsername());

            User user = userService.findByEmail(userDetails.getUsername())
                    .orElseThrow(() -> new RuntimeException("User not found: " + userDetails.getUsername()));

            Cart updatedCart = cartService.addProductToCart(user, cartItemDTO);
            logger.info("Product added/updated in cart for user: {}", userDetails.getUsername());
            logger.info("Attempting to return updated cart object: {}", updatedCart.getId()); // DEBUG: Log cart ID
            return ResponseEntity.ok(updatedCart); // Return the updated cart object
        } catch (RuntimeException e) {
            logger.error("Error adding product to cart: {}", e.getMessage(), e); // LOG STACK TRACE
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            logger.error("An unexpected error occurred while adding product to cart: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred.");
        }
    }

    @GetMapping
    public ResponseEntity<?> getCart(@AuthenticationPrincipal UserDetails userDetails) {
        try {
            logger.info("Received request to get cart for user: {}", userDetails.getUsername());
            User user = userService.findByEmail(userDetails.getUsername())
                    .orElseThrow(() -> new RuntimeException("User not found: " + userDetails.getUsername()));

            Optional<Cart> cart = cartService.getCartByUser(user);
            if (cart.isPresent()) {
                logger.info("Cart found for user: {}", userDetails.getUsername());
                return ResponseEntity.ok(cart.get());
            } else {
                logger.info("No cart found for user: {}", userDetails.getUsername());
                // Return an empty cart object or a specific DTO for an empty cart
                // Sending a string "No cart found for this user." will cause frontend JSON
                // parsing errors.
                // It's better to return an empty JSON object or a DTO representing an empty
                // cart.
                return ResponseEntity.ok(new Cart()); // Return an empty Cart object or a CartDTO
            }
        } catch (RuntimeException e) {
            logger.error("Error getting cart: {}", e.getMessage(), e); // Log stack trace
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            logger.error("An unexpected error occurred while getting cart: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred.");
        }
    }
}
