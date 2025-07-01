// src/main/java/com/eshop/e_shop_backend/dto/MessageResponse.java
package com.eshop.e_shop_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * A simple DTO for sending generic messages (success/error)
 * as JSON responses from the backend.
 */
@Data // Lombok: Generates getters, setters, equals, hashCode, toString
@NoArgsConstructor // Lombok: Generates no-argument constructor
@AllArgsConstructor // Lombok: Generates constructor with all fields
public class MessageResponse {
    private String message;
}
