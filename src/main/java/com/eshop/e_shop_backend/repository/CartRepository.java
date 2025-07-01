// src/main/java/com/eshop/e_shop_backend/repository/CartRepository.java
package com.eshop.e_shop_backend.repository;

import com.eshop.e_shop_backend.model.Cart;
import com.eshop.e_shop_backend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<Cart, String> {
    Optional<Cart> findByUser(User user);
}
