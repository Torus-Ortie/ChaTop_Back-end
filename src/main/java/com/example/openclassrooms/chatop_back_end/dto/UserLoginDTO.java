package com.example.openclassrooms.chatop_back_end.dto;

import lombok.Data;

@Data
public class UserLoginDTO {

    private String name;
    private String password;
    private String email;

}
