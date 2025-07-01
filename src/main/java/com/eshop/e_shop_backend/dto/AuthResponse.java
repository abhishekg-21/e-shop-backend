// src/main/java/com/eshop/e_shop_backend/dto/AuthResponse.java
package com.eshop.e_shop_backend.dto;

import java.util.Set; // Import Set

/**
 * Data Transfer Object for authentication responses.
 * Contains the JWT token, a message, and the user's roles upon successful login
 * or registration.
 */
public class AuthResponse {
    private String token;
    private String message;
    private Set<String> roles; // NEW: Field to hold user roles

    public AuthResponse(String token, String message, Set<String> roles) { // NEW: Updated constructor
        this.token = token;
        this.message = message;
        this.roles = roles; // Assign roles
    }

    // Getters and Setters
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Set<String> getRoles() { // NEW: Getter for roles
        return roles;
    }

    public void setRoles(Set<String> roles) { // NEW: Setter for roles
        this.roles = roles;
    }
}
