// src/main/java/com/eshop/dto/LoginResponse.java
package com.eshop.e_shop_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

/**
 * DTO for login responses.
 * Contains the JWT access token, username (email), and the user's roles.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginResponse {
    private String accessToken;
    private String username; // Typically the email or a unique identifier
    private Set<String> roles; // List of roles (e.g., "ROLE_USER", "ROLE_ADMIN")
}