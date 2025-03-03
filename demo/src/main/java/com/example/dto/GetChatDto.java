package com.example.dto;

import lombok.Data;

@Data
public class GetChatDto {
    private Integer chatId;
    private String chatName;
    public GetChatDto(Integer chatId, String chatName) {
        this.chatId = chatId;
        this.chatName = chatName;
    }
}
