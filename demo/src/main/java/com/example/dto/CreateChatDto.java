package com.example.dto;

import java.util.List;

import lombok.Data;


@Data
public class CreateChatDto {
    private List<Integer> usersId;
}
