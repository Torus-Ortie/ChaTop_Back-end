package com.example.openclassrooms.chatop_back_end.services;


import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.openclassrooms.chatop_back_end.models.User;
import com.example.openclassrooms.chatop_back_end.repositories.UserRepository;

import lombok.Data;

@Data
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public Optional<User> getUserById(final Long id) {
        return userRepository.findById(id);
    }

    public Iterable<User> getUsers() {
        return userRepository.findAll();
    }

    public void deleteUserById(final Long id) {
        userRepository.deleteById(id);
    }

    public User saveUser(User user) {
        User savedEmployee = userRepository.save(user);
        return savedEmployee;
    }
}
