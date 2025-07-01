// src/main/java/com/eshop/e_shop_backend/exception/DuplicateResourceException.java
package com.eshop.e_shop_backend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Custom exception for when an attempt is made to create a resource
 * that already exists (e.g., duplicate email).
 * Maps to HTTP 409 Conflict.
 */
@ResponseStatus(HttpStatus.CONFLICT) // This annotation makes Spring respond with 409 Conflict
public class DuplicateResourceException extends RuntimeException {
    public DuplicateResourceException(String message) {
        super(message);
    }

    public DuplicateResourceException(String message, Throwable cause) {
        super(message, cause);
    }
}
