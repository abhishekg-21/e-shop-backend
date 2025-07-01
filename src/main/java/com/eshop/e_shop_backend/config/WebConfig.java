// src/main/java/com/eshop/config/WebConfig.java
package com.eshop.e_shop_backend.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

// @Configuration // Uncomment if you need this for other WebMvcConfigurer features
// @EnableWebMvc // Uncomment if you want full control over Spring MVC config, often not needed with Spring Boot
public class WebConfig implements WebMvcConfigurer {

    /**
     * Configures CORS (Cross-Origin Resource Sharing) for the application.
     * This method provides an alternative or additional way to configure CORS
     * compared to the CorsConfigurationSource bean in SecurityConfig.
     *
     * Note: If CorsConfigurationSource is defined in SecurityConfig, it generally
     * takes precedence for requests handled by Spring Security.
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // Apply CORS to all paths
                .allowedOrigins("*") // Allow all origins for development. In production, specify your frontend
                                     // URL(s).
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH") // Allowed HTTP methods
                .allowedHeaders("*") // Allow all headers
                .allowCredentials(true) // Allow sending cookies/auth headers
                .maxAge(3600); // Max age for pre-flight requests (in seconds)
    }

    /*
     * // Example: If your static files were NOT in src/main/resources/static, you'd
     * configure a resource handler here
     * 
     * @Override
     * public void addResourceHandlers(ResourceHandlerRegistry registry) {
     * registry.addResourceHandler("/my-static/**")
     * .addResourceLocations("classpath:/my-custom-static-folder/");
     * }
     */
}