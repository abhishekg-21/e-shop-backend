// src/main/java/com/eshop/model/OrderStatus.java
package com.eshop.e_shop_backend.model;

/**
 * Enum representing the possible statuses of an order.
 */
public enum OrderStatus {
    PENDING,
    PROCESSING,
    SHIPPED,
    DELIVERED,
    CANCELLED
}