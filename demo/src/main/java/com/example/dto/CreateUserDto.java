package com.example.dto;

import lombok.Data;

@Data
public class CreateUserDto {
    private String login;
    private String password;
    private String confirm_password;
    private String email;
}