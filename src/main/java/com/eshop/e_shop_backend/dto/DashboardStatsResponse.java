// src/main/java/com/eshop/e_shop_backend/dto/DashboardStatsResponse.java
package com.eshop.e_shop_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data // Generates getters, setters, toString, equals, and hashCode
@AllArgsConstructor // Generates a constructor with all fields
@NoArgsConstructor // Generates a no-argument constructor
public class DashboardStatsResponse {
    private long totalUsers;
    private long totalProducts;
    private long pendingOrders;
    private BigDecimal totalRevenue; // Use BigDecimal for currency calculations
}
