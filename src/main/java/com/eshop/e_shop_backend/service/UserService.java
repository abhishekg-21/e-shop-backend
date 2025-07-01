// src/main/java/com/eshop/e_shop_backend/service/UserService.java
package com.eshop.e_shop_backend.service;

import com.eshop.e_shop_backend.model.User; // Import User
import java.util.Optional; // Import Optional

public interface UserService {
    // Add this method
    Optional<User> findByEmail(String email);

    // You might have other methods here, e.g.,
    // User registerUser(User user);
    // User updateUser(String id, User userDetails);
    // void deleteUser(String id);
    // List<User> findAllUsers();
}
