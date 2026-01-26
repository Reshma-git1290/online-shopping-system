package com.lloyds.onlineshoppingsystem.repository;

import com.lloyds.onlineshoppingsystem.dto.LoginRequest;
import com.lloyds.onlineshoppingsystem.dto.RegisterRequest;
import com.lloyds.onlineshoppingsystem.model.User;
import org.springframework.web.bind.annotation.RequestBody;

public interface UserRepository {
    User register(RegisterRequest registerRequest);
    String login(LoginRequest loginRequest);
}
