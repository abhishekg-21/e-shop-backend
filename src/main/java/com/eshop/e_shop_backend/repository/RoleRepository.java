// src/main/java/com/eshop/e_shop_backend/repository/RoleRepository.java
package com.eshop.e_shop_backend.repository;

import com.eshop.e_shop_backend.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository // Marks this as a Spring Data JPA repository
public interface RoleRepository extends JpaRepository<Role, String> { // Assuming Role ID is String (UUID)

    // Method to find a Role by its name. Spring Data JPA will implement this
    // automatically.
    Optional<Role> findByName(String name); // <<<--- THIS METHOD IS CRITICAL

    // Optional: Check if a role with a given name exists
    boolean existsByName(String name);
}
