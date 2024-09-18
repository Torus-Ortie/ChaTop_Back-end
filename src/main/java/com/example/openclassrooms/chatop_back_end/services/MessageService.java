package com.example.openclassrooms.chatop_back_end.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.openclassrooms.chatop_back_end.models.Message;
import com.example.openclassrooms.chatop_back_end.repositories.MessageRepository;

import lombok.Data;

@Data
@Service
public class MessageService {

    @Autowired
    private MessageRepository messageRepository;

    public Optional<Message> getMessageById(final Long id) {
        return messageRepository.findById(id);
    }

    public Iterable<Message> getMessages() {
        return messageRepository.findAll();
    }

    public void deleteMessageById(final Long id) {
        messageRepository.deleteById(id);
    }

    public Message saveMessage(Message message) {
        Message savedEmployee = messageRepository.save(message);
        return savedEmployee;
    }
}
