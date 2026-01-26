package com.lloyds.onlineshoppingsystem.service;

import com.lloyds.onlineshoppingsystem.dto.LoginRequest;
import com.lloyds.onlineshoppingsystem.dto.RegisterRequest;
import com.lloyds.onlineshoppingsystem.model.Cart;
import com.lloyds.onlineshoppingsystem.model.User;
import com.lloyds.onlineshoppingsystem.repository.UserRepository;
import com.lloyds.onlineshoppingsystem.repository.jpa.CartJpaRepository;
import com.lloyds.onlineshoppingsystem.repository.jpa.UserJpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserService implements UserRepository {

    private final UserJpaRepository userRepo;
    private final CartJpaRepository cartRepo;

    public UserService(UserJpaRepository userRepo, CartJpaRepository cartRepo) {
        this.userRepo = userRepo;
        this.cartRepo = cartRepo;
    }

    public User register(RegisterRequest request) {

        if (userRepo.findByEmail(request.getEmail()).isPresent()) {
            throw new RuntimeException("User already exists");
        }

        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword()); // later use BCrypt

        User savedUser = userRepo.save(user);

        // create cart for new user
        Cart cart = new Cart(savedUser);
        cartRepo.save(cart);

        return savedUser;
    }

    public String login(LoginRequest request) {

        User user = userRepo.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!user.getPassword().equals(request.getPassword())) {
            throw new RuntimeException("Invalid password");
        }

        return "You successfully Logged in!";
    }
}
