package com.lloyds.onlineshoppingsystem.controller;

import com.lloyds.onlineshoppingsystem.dto.LoginRequest;
import com.lloyds.onlineshoppingsystem.dto.RegisterRequest;
import com.lloyds.onlineshoppingsystem.model.User;
import com.lloyds.onlineshoppingsystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public User register(@RequestBody RegisterRequest request) {
        return userService.register(request);
    }

    @PostMapping("/login")
    public String login(@RequestBody LoginRequest request) {
        return userService.login(request);
    }


}
