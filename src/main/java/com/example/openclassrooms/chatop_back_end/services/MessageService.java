package com.example.openclassrooms.chatop_back_end.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.openclassrooms.chatop_back_end.dto.MessageDTO;
import com.example.openclassrooms.chatop_back_end.models.Message;
import com.example.openclassrooms.chatop_back_end.repositories.MessageRepository;
import com.example.openclassrooms.chatop_back_end.repositories.RentalRepository;
import com.example.openclassrooms.chatop_back_end.repositories.UserRepository;

import lombok.Data;

@Data
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
        message.setMessage(messageDTO.getMessage());
        message.setUser(userRepository.findById(messageDTO.getUser_id()).get());
        message.setRental(rentalRepository.findById(messageDTO.getRental_id()).get());
        messageRepository.save(message);
    }
}
