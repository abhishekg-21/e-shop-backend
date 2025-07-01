// src/main/java/com/eshop/e_shop_backend/service/UserServiceImpl.java
package com.eshop.e_shop_backend.service;

import com.eshop.e_shop_backend.model.User;
import com.eshop.e_shop_backend.repository.UserRepository; // Import UserRepository
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional; // Ensure this import is correct

import java.util.Optional;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    // Add other User service methods you might have here, e.g.:
    // @Override
    // public User registerUser(User user) {
    // // ... implementation
    // }
}
