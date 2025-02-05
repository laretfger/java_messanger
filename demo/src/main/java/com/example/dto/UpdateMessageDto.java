package com.example.dto;

import lombok.Data;


@Data
public class UpdateMessageDto {
    private String newData;
    private Integer messageId;
    private Integer chatId;
}