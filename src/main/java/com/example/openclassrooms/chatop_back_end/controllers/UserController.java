package com.example.openclassrooms.chatop_back_end.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.example.openclassrooms.chatop_back_end.dto.UserDTO;
import com.example.openclassrooms.chatop_back_end.services.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/user/{id}")
    public UserDTO getUserById(@PathVariable("id") final Long id) {
        return userService.getUserById(id);
    }
    
}
