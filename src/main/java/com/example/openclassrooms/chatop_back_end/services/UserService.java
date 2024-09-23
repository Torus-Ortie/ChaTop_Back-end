package com.example.openclassrooms.chatop_back_end.services;


import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.openclassrooms.chatop_back_end.models.User;
import com.example.openclassrooms.chatop_back_end.repositories.UserRepository;

import lombok.Data;

@Data
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public Optional<User> getUserById(final Long id) {
        return userRepository.findById(id);
    }

    public User registerNewUser(User user) {
        User newUser = new User();
        newUser.setEmail(user.getEmail());
        newUser.setName(user.getName());
        newUser.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(newUser);
    }

    public User getCurrentUser(Authentication authentication) {
        String email = authentication.getName();
        return userRepository.findByEmail(email);
    }
}
