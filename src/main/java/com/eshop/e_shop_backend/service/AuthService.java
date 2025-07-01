// src/main/java/com/eshop/e_shop_backend/service/AuthService.java
package com.eshop.e_shop_backend.service;

import com.eshop.e_shop_backend.dto.AuthResponse;
import com.eshop.e_shop_backend.dto.DashboardStatsResponse;
import com.eshop.e_shop_backend.dto.LoginRequest;
import com.eshop.e_shop_backend.dto.RegisterRequest;
import org.springframework.security.core.Authentication;
import org.springframework.http.ResponseEntity; // Needed for validateAdminAccess

public interface AuthService {
    AuthResponse login(LoginRequest loginRequest);

    AuthResponse register(RegisterRequest registerRequest);

    ResponseEntity<?> validateAdminAccess(Authentication authentication);

    DashboardStatsResponse getDashboardStats();
}
