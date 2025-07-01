// src/main/java/com/eshop/e_shop_backend/controller/AuthController.java
package com.eshop.e_shop_backend.controller;

import com.eshop.e_shop_backend.dto.AuthResponse;
import com.eshop.e_shop_backend.dto.LoginRequest;
import com.eshop.e_shop_backend.dto.RegisterRequest;
import com.eshop.e_shop_backend.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth") // Base path for authentication endpoints
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody RegisterRequest request) {
        return ResponseEntity.ok(authService.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }

    /**
     * Endpoint to validate if the authenticated user has ADMIN privileges.
     * The frontend JavaScript (admin-users.js, admin-products.js) calls this.
     * It MUST be a GET request and match the path used by the frontend.
     */
    @GetMapping("/admin/validate") // <--- THIS IS THE CRITICAL ANNOTATION
    @PreAuthorize("hasRole('ADMIN')") // Ensures only users with ROLE_ADMIN can access
    public ResponseEntity<String> validateAdmin() {
        // If the request reaches this point, it means:
        // 1. The JWT token was valid.
        // 2. The user extracted from the token has the 'ADMIN' role.
        return ResponseEntity.ok("Admin access granted.");
    }

    // Add other endpoints as needed, e.g., for password reset, etc.
}
