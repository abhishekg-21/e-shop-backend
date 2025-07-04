// src/main/java/com/eshop/e_shop_backend/security/SecurityConfig.java
package com.eshop.e_shop_backend.security;

import com.eshop.e_shop_backend.service.CustomUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.cors.CorsConfigurationSource;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity // Enables @PreAuthorize
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthFilter;
    private final CustomUserDetailsService customUserDetailsService;

    public SecurityConfig(JwtAuthenticationFilter jwtAuthFilter, CustomUserDetailsService customUserDetailsService) {
        this.jwtAuthFilter = jwtAuthFilter;
        this.customUserDetailsService = customUserDetailsService;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(AbstractHttpConfigurer::disable) // Disable CSRF for API endpoints
            .cors(cors -> cors.configurationSource(corsConfigurationSource())) // Enable CORS
            .authorizeHttpRequests(authorize -> authorize
                // --- Public Endpoints (accessible without authentication) ---

                // Allow all static resources (CSS, JS, images, HTML pages)
                .requestMatchers(
                    "/", // Root path (index.html if mapped)
                    "/index.html",
                    "/login.html",
                    "/register.html",
                    "/products.html",
                    "/cart.html",
                    "/admin.html",
                    "/users.html",
                    "/css/**",
                    "/js/**",
                    "/images/**",
                    "/favicon.ico"
                ).permitAll()

                // Allow authentication API endpoints (login, register)
                .requestMatchers("/api/auth/**").permitAll()

                // Allow GET requests to products and categories API endpoints (publicly viewable)
                // IMPORTANT: Ensure these are specific and come BEFORE any general authenticated() rule
                .requestMatchers(HttpMethod.GET, "/api/products", "/api/products/**").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/categories").permitAll()

                // --- Authenticated Endpoints (require JWT token) ---

                // User-specific API endpoints (require authentication and ROLE_USER)
                .requestMatchers("/api/cart/**").hasRole("USER") // Cart operations require user role
                .requestMatchers("/api/orders/**").hasRole("USER") // Order operations require user role

                // Admin-specific API endpoints (require authentication and ROLE_ADMIN)
                .requestMatchers("/api/users/**").hasRole("ADMIN") // User management API
                // Add other admin-specific APIs here: .requestMatchers("/api/admin/**").hasRole("ADMIN")

                // Any other request not explicitly permitted or role-based requires authentication
                .anyRequest().authenticated()
            )
            .sessionManagement(session -> session
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS) // Use stateless sessions for JWT
            )
            .authenticationProvider(authenticationProvider())
            .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(customUserDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        // Allow requests from your frontend's domain (localhost:5500 for dev, Netlify URL for prod)
        configuration.setAllowedOrigins(Arrays.asList(
            "http://localhost:8080", // If you also run frontend from 8080
            "http://127.0.0.1:8080",
            "http://localhost:5500" // Add this for your current frontend testing
            // Add your Netlify URL here when your frontend is deployed, e.g., "https://your-netlify-app-url.netlify.app"
        ));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH"));
        configuration.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type", "Accept"));
        configuration.setAllowCredentials(true); // Allow cookies, authorization headers, etc.
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration); // Apply CORS to all paths
        return source;
    }
}
