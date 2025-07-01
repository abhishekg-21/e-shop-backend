// src/main/java/com/eshop/e_shop_backend/controller/RoleController.java
package com.eshop.e_shop_backend.controller;

import com.eshop.e_shop_backend.model.Role;
import com.eshop.e_shop_backend.repository.RoleRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize; // For method-level security
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * REST Controller for retrieving roles.
 */
@RestController
@RequestMapping("/api/roles") // Endpoint for roles
@CrossOrigin(origins = "http://localhost:8080")
public class RoleController {

    private final RoleRepository roleRepository;

    public RoleController(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    /**
     * Retrieves all available roles.
     * Accessible only by users with ROLE_ADMIN.
     *
     * @return A list of Role objects.
     */
    @GetMapping
    // @PreAuthorize("hasRole('ADMIN')") // Alternative: method-level security
    public ResponseEntity<List<Role>> getAllRoles() {
        List<Role> roles = roleRepository.findAll();
        return ResponseEntity.ok(roles);
    }
}
