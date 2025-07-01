// src/main/java/com/eshop/e_shop_backend/dto/UserDTO.java
package com.eshop.e_shop_backend.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

/**
 * DTO for transferring User data.
 * Used for both input (create/update) and output (read).
 */
@Data // Lombok: Generates getters, setters, equals, hashCode, toString
@NoArgsConstructor // Lombok: Generates no-argument constructor
@AllArgsConstructor // Lombok: Generates constructor with all fields
public class UserDTO {
    private String id; // ID is usually null for new users, populated for existing

    @NotBlank(message = "First name is required")
    private String firstName;

    @NotBlank(message = "Last name is required")
    private String lastName;

    @Email(message = "Invalid email format")
    @NotBlank(message = "Email is required")
    private String email;

    // Password is required for new users, optional for updates (leave blank to keep
    // current)
    @Size(min = 6, message = "Password must be at least 6 characters long", groups = { NewUserValidation.class })
    private String password;

    @Pattern(regexp = "^$|^[0-9\\-()\\s]{7,20}$", message = "Invalid phone number format")
    private String phoneNumber;

    private boolean enabled; // Whether the user account is enabled

    // Roles are typically sent as a Set of Strings (role names like "ROLE_ADMIN")
    // The backend will then map these to actual Role entities.
    @NotBlank(message = "At least one role is required", groups = { NewUserValidation.class,
            ExistingUserValidation.class })
    private Set<String> roles;

    // Inner interfaces for validation groups (optional, but good for conditional
    // validation)
    public interface NewUserValidation {
    }

    public interface ExistingUserValidation {
    }
}
