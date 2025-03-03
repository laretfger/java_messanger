package com.example.dto;

import java.time.LocalDate;

import lombok.Data;

@Data
public class GetMessageDto {
    private Integer messageId;
    private String data;
    private Integer ownerUserId;
    private LocalDate date;
    public GetMessageDto(Integer messageId, String data, Integer ownerUserId) {
        this.messageId = messageId;
        this.data = data;
        this.ownerUserId = ownerUserId;
        this.date = LocalDate.now();
    }
}
