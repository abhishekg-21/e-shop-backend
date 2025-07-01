// src/main/java/com/eshop/e_shop_backend/dto/RegistrationRequest.java
package com.eshop.e_shop_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO for new user registration requests.
 * Includes explicit first name, last name, and phone number for user details.
 */
@Data // Generates getters, setters, toString, equals, and hashCode
@AllArgsConstructor // Generates a constructor with all fields
@NoArgsConstructor // Generates a no-argument constructor
public class RegistrationRequest {
    private String firstName; // Add this field
    private String lastName; // Add this field
    private String email;
    private String password;
    private String phoneNumber; // Add this field
    // You might remove 'name' if you're using firstName and lastName instead.
    // If you still use 'name' on the frontend for registration, you'll need to
    // adapt.
    // For now, I'm assuming registration form collects firstName, lastName, email,
    // password, phoneNumber.
}
