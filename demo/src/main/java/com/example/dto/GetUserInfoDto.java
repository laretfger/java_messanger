package com.example.dto;

import lombok.Data;

@Data
public class GetUserInfoDto {
    private String email;
    private String login;

    public GetUserInfoDto(String email, String login) {
        this.email = email;
        this.login = login;
    }
}
