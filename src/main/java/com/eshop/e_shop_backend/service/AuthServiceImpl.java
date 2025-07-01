// src/main/java/com/eshop/e_shop_backend/service/AuthServiceImpl.java
package com.eshop.e_shop_backend.service;

import com.eshop.e_shop_backend.dto.AuthResponse;
import com.eshop.e_shop_backend.dto.DashboardStatsResponse;
import com.eshop.e_shop_backend.dto.LoginRequest;
import com.eshop.e_shop_backend.dto.RegisterRequest;
import com.eshop.e_shop_backend.dto.UserInfoResponse;
import com.eshop.e_shop_backend.model.Role;
import com.eshop.e_shop_backend.model.User;
import com.eshop.e_shop_backend.repository.OrderRepository;
import com.eshop.e_shop_backend.repository.ProductRepository;
import com.eshop.e_shop_backend.repository.RoleRepository;
import com.eshop.e_shop_backend.repository.UserRepository;
import com.eshop.e_shop_backend.security.JwtUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service; // Make sure this import is present
import jakarta.transaction.Transactional;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Implementation of AuthService for user authentication and registration.
 */
@Service // <--- THIS ANNOTATION IS CRUCIAL!
@Transactional
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;
    private final CustomUserDetailsService customUserDetailsService;

    public AuthServiceImpl(AuthenticationManager authenticationManager,
            UserRepository userRepository,
            RoleRepository roleRepository,
            PasswordEncoder passwordEncoder,
            JwtUtil jwtUtil,
            ProductRepository productRepository,
            OrderRepository orderRepository,
            CustomUserDetailsService customUserDetailsService) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
        this.productRepository = productRepository;
        this.orderRepository = orderRepository;
        this.customUserDetailsService = customUserDetailsService;
    }

    @Override
    public AuthResponse login(LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        String jwt = jwtUtil.generateToken(userDetails);

        Set<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toSet());

        return new AuthResponse(jwt, "Login successful!", roles);
    }

    @Override
    public AuthResponse register(RegisterRequest registerRequest) {
        if (userRepository.existsByEmail(registerRequest.getEmail())) {
            throw new RuntimeException("Email is already taken!");
        }

        User user = new User();
        user.setFirstName(registerRequest.getFirstName());
        user.setLastName(registerRequest.getLastName());
        user.setEmail(registerRequest.getEmail());
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        user.setPhoneNumber(registerRequest.getPhoneNumber());
        user.setEnabled(true);

        Role userRole = roleRepository.findByName("ROLE_USER")
                .orElseThrow(
                        () -> new RuntimeException("Error: ROLE_USER not found. Please ensure roles are initialized."));
        user.setRoles(Collections.singleton(userRole));

        userRepository.save(user);

        UserDetails userDetails = customUserDetailsService.loadUserByUsername(user.getEmail());
        String jwt = jwtUtil.generateToken(userDetails);

        Set<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toSet());

        return new AuthResponse(jwt, "User registered successfully!", roles);
    }

    @Override
    public ResponseEntity<?> validateAdminAccess(Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        Set<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toSet());
        if (roles.contains("ROLE_ADMIN")) {
            String name = userRepository.findByEmail(userDetails.getUsername())
                    .map(user -> user.getFirstName() + " " + user.getLastName())
                    .orElse(userDetails.getUsername());
            return ResponseEntity.ok(new UserInfoResponse(name, roles));
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Access Denied: User is not an ADMIN.");
    }

    @Override
    public DashboardStatsResponse getDashboardStats() {
        long totalUsers = userRepository.count();
        long totalProducts = productRepository.count();
        long pendingOrders = orderRepository.countByOrderStatus("PENDING");
        BigDecimal totalRevenue = orderRepository.sumTotalAmountByOrderStatus("DELIVERED")
                .orElse(BigDecimal.ZERO);
        return new DashboardStatsResponse(totalUsers, totalProducts, pendingOrders, totalRevenue);
    }
}
