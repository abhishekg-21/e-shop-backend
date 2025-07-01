// src/main/java/com/eshop/e_shop_backend/dto/LoginRequest.java
package com.eshop.e_shop_backend.dto;

/**
 * Data Transfer Object for user login requests.
 * Contains user credentials (email and password) for authentication.
 */
public class LoginRequest {
    private String email;
    private String password;

    // Getters and Setters
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
