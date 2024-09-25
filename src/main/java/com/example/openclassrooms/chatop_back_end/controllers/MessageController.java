package com.example.openclassrooms.chatop_back_end.controllers;

import java.util.Collections;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.openclassrooms.chatop_back_end.dto.MessageDTO;
import com.example.openclassrooms.chatop_back_end.services.MessageService;

@RestController
public class MessageController {

    @Autowired
    private MessageService messageService;

    @PostMapping("/messages")
    public ResponseEntity<Map<String, String>> createMessage(@RequestBody MessageDTO messageDTO) {
        messageService.saveMessage(messageDTO);
        return new ResponseEntity<>(Collections.singletonMap("message", "Message sent."), HttpStatus.CREATED);
    }

}
