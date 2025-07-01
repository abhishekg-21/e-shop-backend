// src/main/java/com/eshop/e_shop_backend/config/RoleInitializer.java
package com.eshop.e_shop_backend.config;

import com.eshop.e_shop_backend.model.Role;
import com.eshop.e_shop_backend.repository.RoleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

// Optional: Use if you don't already have roles like 'ROLE_USER' and 'ROLE_ADMIN'
// in your database. This ensures they exist on startup.
@Component
public class RoleInitializer implements CommandLineRunner {

    private final RoleRepository roleRepository;

    public RoleInitializer(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        // Check if roles exist, if not, create them
        if (roleRepository.findByName("ROLE_USER").isEmpty()) {
            Role userRole = new Role();
            userRole.setName("ROLE_USER");
            roleRepository.save(userRole);
            System.out.println("Created ROLE_USER");
        }

        if (roleRepository.findByName("ROLE_ADMIN").isEmpty()) {
            Role adminRole = new Role();
            adminRole.setName("ROLE_ADMIN");
            roleRepository.save(adminRole);
            System.out.println("Created ROLE_ADMIN");
        }
    }
}
