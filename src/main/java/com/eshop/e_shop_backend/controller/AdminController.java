// src/main/java/com/eshop/e_shop_backend/controller/AdminController.java
package com.eshop.e_shop_backend.controller;

import com.eshop.e_shop_backend.dto.DashboardStatsResponse;
import com.eshop.e_shop_backend.service.AuthService; // Use AuthService to get stats
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize; // For @PreAuthorize
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST Controller for administrator-specific operations.
 * All endpoints in this controller should typically be protected by the ADMIN
 * role.
 */
@RestController
@RequestMapping("/api/admin") // Base path for admin-specific endpoints
public class AdminController {

    private final AuthService authService; // Inject AuthService to access dashboard stats logic

    public AdminController(AuthService authService) {
        this.authService = authService;
    }

    /**
     * Endpoint to fetch aggregated dashboard statistics for administrators.
     * Requires ADMIN role.
     * 
     * @return ResponseEntity with DashboardStatsResponse containing various counts
     *         and totals.
     */
    @GetMapping("/dashboard-stats")
    @PreAuthorize("hasRole('ADMIN')") // Ensures only users with 'ADMIN' role can access this
    public ResponseEntity<DashboardStatsResponse> getDashboardStatistics() {
        DashboardStatsResponse stats = authService.getDashboardStats();
        return ResponseEntity.ok(stats);
    }

    // You would add other admin-related endpoints here, e.g., for products, users,
    // orders management
    // Example:
    // @GetMapping("/products")
    // @PreAuthorize("hasRole('ADMIN')")
    // public ResponseEntity<Page<ProductDTO>> getAllProductsForAdmin(...) { ... }
}
