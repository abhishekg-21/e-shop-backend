// src/main/java/com/eshop/dto/UserInfoResponse.java
package com.eshop.e_shop_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

/**
 * DTO for sending basic user information (username and roles) to the frontend.
 * Used for authentication status and role-based UI adjustments.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserInfoResponse {
    private String username;
    private Set<String> roles; // User's roles (e.g., "ROLE_USER", "ROLE_ADMIN")
}