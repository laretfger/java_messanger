package com.example.controller;

import java.security.Principal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;

import com.example.dto.CreateMessageDto;
import com.example.dto.DeleteMessageDto;
import com.example.dto.UpdateMessageDto;
import com.example.entities.Message;
import com.example.entities.User;
import com.example.service.ServiceMessage;
import com.example.service.ServiceUser;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class MessageController {
    public final ServiceMessage messageService;
    public final ServiceUser userService;
    private final SimpMessagingTemplate messagingTemplate;

    @MessageMapping("/send")
    public String sendMessage(CreateMessageDto createMessageDto) {
        User user = userService.findByLogin(SecurityContextHolder.getContext().getAuthentication().getName()).orElseThrow(() -> new NullPointerException());
        Integer userId = user.getId();
        Message message;

        try{
            message = messageService.create(createMessageDto, userId);
        }
        catch(Exception e) {
            return e.getMessage();
        }

        messagingTemplate.convertAndSend("/topic/messages/" + createMessageDto.getChatId(), message);

        return "Отправленно!";

    }

    @GetMapping("/getMessages")
    public ResponseEntity<?> getMessages(@RequestBody CreateMessageDto createMessageDto) {
        User user = userService.findByLogin(SecurityContextHolder.getContext().getAuthentication().getName()).orElseThrow(() -> new NullPointerException());
        Integer userId = user.getId();
        return messageService.getAllMessage(createMessageDto.getChatId(), userId);
    }

    @PutMapping("/updateMessage")
    public ResponseEntity<?> update(@RequestBody UpdateMessageDto updateMessgeDto) {
        User user = userService.findByLogin(SecurityContextHolder.getContext().getAuthentication().getName()).orElseThrow(() -> new NullPointerException());
        Integer userId = user.getId();
        return messageService.update(userId, updateMessgeDto);
    }

    @DeleteMapping("/deleteMessage")
    public ResponseEntity<?> delete(@RequestBody DeleteMessageDto deleteMessageDto) {
        User user = userService.findByLogin(SecurityContextHolder.getContext().getAuthentication().getName()).orElseThrow(() -> new NullPointerException());
        Integer userId = user.getId();
        return messageService.delete(deleteMessageDto.getChatId(), userId, deleteMessageDto.getMessageId());
    }
}