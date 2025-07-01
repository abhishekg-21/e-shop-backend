// src/main/java/com/eshop/service/CustomUserDetailsService.java
package com.eshop.e_shop_backend.service;

import com.eshop.e_shop_backend.model.User;
import com.eshop.e_shop_backend.repository.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Custom implementation of Spring Security's UserDetailsService.
 * Loads user details (username, password, roles) from the database.
 */
@Service // Marks this as a Spring service component
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    // Constructor injection for UserRepository
    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Locates the user based on the username (email in this case).
     * 
     * @param email The email address of the user.
     * @return A UserDetails object for Spring Security.
     * @throws UsernameNotFoundException if the user could not be found.
     */
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        // Find user by email in the database
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));

        // Convert our custom User entity and its roles into Spring Security's
        // UserDetails object
        return new org.springframework.security.core.userdetails.User(
                user.getEmail(), // Username (email)
                user.getPassword(), // Hashed password
                mapRolesToAuthorities(user.getRoles()) // User's roles mapped to GrantedAuthority
        );
    }

    /**
     * Helper method to map our custom Role entities to Spring Security's
     * GrantedAuthority objects.
     * 
     * @param roles The collection of Role entities.
     * @return A collection of GrantedAuthority.
     */
    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(
            Set<com.eshop.e_shop_backend.model.Role> roles) {
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority(role.getName())) // Convert Role name to SimpleGrantedAuthority
                .collect(Collectors.toList());
    }
}