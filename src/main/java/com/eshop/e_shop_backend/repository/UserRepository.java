// src/main/java/com/eshop/e_shop_backend/repository/UserRepository.java
package com.eshop.e_shop_backend.repository;

import com.eshop.e_shop_backend.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
        Optional<User> findByEmail(String email);

        // Add this method declaration
        Boolean existsByEmail(String email);

        /**
         * Finds users by first name, last name, or email (case-insensitive).
         * This method is used when only a search term is provided.
         *
         * @param firstName Search term for first name.
         * @param lastName  Search term for last name.
         * @param email     Search term for email.
         * @param pageable  Pagination and sorting information.
         * @return A Page of User entities matching the search criteria.
         */
        Page<User> findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCaseOrEmailContainingIgnoreCase(
                        String firstName, String lastName, String email, Pageable pageable);

        /**
         * Finds users by role name.
         * This method is used when only a role filter is provided.
         *
         * @param roleName The name of the role (e.g., "ROLE_ADMIN", "ROLE_USER").
         * @param pageable Pagination and sorting information.
         * @return A Page of User entities with the specified role.
         */
        Page<User> findByRoles_Name(String roleName, Pageable pageable);

        /**
         * Finds users by first name, last name, or email (case-insensitive) AND by role
         * name.
         * This method combines both search and role filtering.
         *
         * @param firstName Search term for first name.
         * @param lastName  Search term for last name.
         * @param email     Search term for email.
         * @param roleName  The name of the role to filter by.
         * @param pageable  Pagination and sorting information.
         * @return A Page of User entities matching both search and role criteria.
         */
        Page<User> findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCaseOrEmailContainingIgnoreCaseAndRoles_Name(
                        String firstName, String lastName, String email, String roleName, Pageable pageable);
}
