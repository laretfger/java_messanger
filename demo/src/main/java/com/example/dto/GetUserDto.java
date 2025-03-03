package com.example.dto;

import lombok.Data;

@Data
public class GetUserDto {
    private String login;
    private String token;
    private Integer userId;

    public GetUserDto(String login, Integer userId, String token) {
        this.login = login;
        this.userId = userId;
        this.token = token;
    }
}
