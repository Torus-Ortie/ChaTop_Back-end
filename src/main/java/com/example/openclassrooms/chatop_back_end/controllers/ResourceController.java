package com.example.openclassrooms.chatop_back_end.controllers;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;


@RestController
public class ResourceController {

    @GetMapping("/")
    public String getResource() {
        return "a value...";
    }
    
}
