package com.example.openclassrooms.chatop_back_end.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class MessageDTO {

    private Long id;
    private Long rental_id;
    private Long user_id;
    private String message;
    private LocalDateTime created_at;
    private LocalDateTime updated_at;

}
