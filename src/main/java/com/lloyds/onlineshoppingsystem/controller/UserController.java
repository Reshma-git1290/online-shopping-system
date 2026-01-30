package com.lloyds.onlineshoppingsystem.controller;

import com.lloyds.onlineshoppingsystem.model.User;
import com.lloyds.onlineshoppingsystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping()
    public List<User> getUsers(){
        return userService.getUsers();
    }
    @GetMapping("/{userId}")
    public User getUserById(@PathVariable Long userId){
        return userService.getUserById(userId);
    }

    @GetMapping("/by-email")
    public User getUserByEmail(@RequestParam String email) {
        return userService.getUserByEmail(email);
    }

    @DeleteMapping("/{userId}")
    public void deleteUser(@PathVariable Long userId)
    {
        userService.deleteUser(userId);
    }
}
