package com.example.openclassrooms.chatop_back_end.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.openclassrooms.chatop_back_end.dto.MessageDTO;
import com.example.openclassrooms.chatop_back_end.models.Message;
import com.example.openclassrooms.chatop_back_end.repositories.MessageRepository;
import com.example.openclassrooms.chatop_back_end.repositories.RentalRepository;
import com.example.openclassrooms.chatop_back_end.repositories.UserRepository;

import java.util.NoSuchElementException;

@Service
public class MessageService {

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RentalRepository rentalRepository;

    public void saveMessage(MessageDTO messageDTO) {
        Message message = new Message();

        // Convert Message to MessageDTO
        message.setMessage(messageDTO.getMessage());
        message.setUser(userRepository.findById(messageDTO.getUser_id()).orElseThrow(() -> new NoSuchElementException("User not found with id : " + messageDTO.getUser_id())));
        message.setRental(rentalRepository.findById(messageDTO.getRental_id()).orElseThrow(() -> new NoSuchElementException("Rental not found with id : " + messageDTO.getUser_id())));
        messageRepository.save(message);
    }
}
