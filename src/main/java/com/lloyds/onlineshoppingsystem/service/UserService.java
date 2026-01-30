package com.lloyds.onlineshoppingsystem.service;

import com.lloyds.onlineshoppingsystem.dto.LoginRequest;
import com.lloyds.onlineshoppingsystem.dto.RegisterRequest;
import com.lloyds.onlineshoppingsystem.model.Cart;
import com.lloyds.onlineshoppingsystem.model.User;
import com.lloyds.onlineshoppingsystem.repository.UserRepository;
import com.lloyds.onlineshoppingsystem.repository.jpa.CartItemJpaRepository;
import com.lloyds.onlineshoppingsystem.repository.jpa.CartJpaRepository;
import com.lloyds.onlineshoppingsystem.repository.jpa.OrderJpaRepository;
import com.lloyds.onlineshoppingsystem.repository.jpa.UserJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@Transactional
public class UserService implements UserRepository {
@Autowired
    private  UserJpaRepository userJpaRepository;
@Autowired
    private  CartJpaRepository cartJpaRepository;
@Autowired
private CartItemJpaRepository cartItemJpaRepository;
@Autowired
private OrderJpaRepository orderJpaRepository;



    public User register(RegisterRequest request) {

        if (userJpaRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new RuntimeException("User already exists");
        }

        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword()); // later use BCrypt

        User savedUser = userJpaRepository.save(user);

        // create cart for new user
        Cart cart = new Cart(savedUser);
        cartJpaRepository.save(cart);

        return savedUser;
    }

    public String login(LoginRequest request) {

        User user = userJpaRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!user.getPassword().equals(request.getPassword())) {
            throw new RuntimeException("Invalid password");
        }

        return "You successfully Logged in!";
    }



    //user controller methods

    public List<User> getUsers(){
        try {
            return userJpaRepository.findAll();
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    public User getUserById(Long userId){
        return userJpaRepository.findById(userId).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND));
    }
    public User getUserByEmail(String email) {
        return userJpaRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found with email: " + email));
    }


    @Transactional
    public void deleteUser(Long userId) {

        User user = userJpaRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // 1) delete cart items first (if user has cart)
        if (user.getCart() != null) {
            Long cartId = user.getCart().getCartid();

            cartItemJpaRepository.deleteByCart_Cartid(cartId); // needs repo method
            cartJpaRepository.deleteById(cartId);
        }

        // 2) delete orders of this user (needs repo method)
        orderJpaRepository.deleteByUser_UserId(userId);

        // 3) finally delete user
        userJpaRepository.deleteById(userId);
    }

}
