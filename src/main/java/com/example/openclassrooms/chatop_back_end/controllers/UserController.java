package com.example.openclassrooms.chatop_back_end.controllers;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.example.openclassrooms.chatop_back_end.models.User;
import com.example.openclassrooms.chatop_back_end.services.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/user/{id}")
    public Optional<User> getUserById(@PathVariable("id") final Long id) {
        return userService.getUserById(id);
    }
    
}
