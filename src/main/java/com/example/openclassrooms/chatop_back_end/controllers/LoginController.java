package com.example.openclassrooms.chatop_back_end.controllers;

import org.springframework.web.bind.annotation.RestController;

import com.example.openclassrooms.chatop_back_end.services.JWTService;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;

@RestController
public class LoginController {

    public JWTService jwtService;

    public LoginController(JWTService jwtService) {
        this.jwtService = jwtService;
    }

    @PostMapping("/login")
    public String getToken(Authentication authentication) {
        String token = jwtService.generteToken(authentication);        
        return token;
    }
    
}
