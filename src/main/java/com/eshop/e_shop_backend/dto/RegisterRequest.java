// src/main/java/com/eshop/e_shop_backend/dto/RegisterRequest.java
package com.eshop.e_shop_backend.dto;

import java.util.Set; // Import Set for roles

/**
 * Data Transfer Object for user registration requests.
 * Contains user details needed for creating a new account.
 */
public class RegisterRequest {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String phoneNumber; // Optional
    private Set<String> roles; // Set of role names (e.g., "ROLE_USER", "ROLE_ADMIN")

    // Getters and Setters
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    // FIX: Corrected typo here from 'voidsetLastName' to 'setLastName'
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

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

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Set<String> getRoles() {
        return roles;
    }

    public void setRoles(Set<String> roles) {
        this.roles = roles;
    }
}
