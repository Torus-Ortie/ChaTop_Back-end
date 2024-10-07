package com.example.openclassrooms.chatop_back_end.services;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.openclassrooms.chatop_back_end.dto.UserDTO;
import com.example.openclassrooms.chatop_back_end.dto.UserLoginDTO;
import com.example.openclassrooms.chatop_back_end.models.User;
import com.example.openclassrooms.chatop_back_end.repositories.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Autowired
    private ModelMapper modelMapper;

    /**
     * Save a new user in Database
     *
     * @param user - The User to save mapped as UserLoginDTO
     *
     */
    public void registerNewUser(UserLoginDTO user) {
        User newUser = new User();

        // Convert UserDTO to User
        newUser.setName(user.getName());
        newUser.setPassword(passwordEncoder.encode(user.getPassword()));
        newUser.setEmail(user.getEmail());
        modelMapper.map(userRepository.save(newUser), UserDTO.class);
    }

    /**
     * Get the current connected user of the session
     *
     * @param authentication - The current session properties
     * @return a User mapped as UserDTO
     *
     */
    public UserDTO getCurrentUser(Authentication authentication) {
        UserDTO currentUser = new UserDTO();
        String email = authentication.getName();
        User user = userRepository.findByEmail(email);

        // Convert User to UserDTO
        currentUser.setId(user.getId());
        currentUser.setEmail(user.getEmail());
        currentUser.setName(user.getName());
        currentUser.setCreated_at(user.getCreated_at());
        currentUser.setUpdated_at(user.getUpdated_at());
        return currentUser;
    }

    /**
     * Get a specific user in Database
     *
     * @param id - The identification of the User to get
     * @return a User mapped as UserDTO
     *
     */
    public UserDTO getUserById(final Long id) {
        return modelMapper.map(userRepository.findById(id), UserDTO.class);
    }
}
