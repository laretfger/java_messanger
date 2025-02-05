package com.example.controller;

import java.io.IOException;
import java.security.Principal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.dto.CreateChatDto;
import com.example.dto.CreateMessageDto;
import com.example.entities.Chat;
import com.example.entities.Message;
import com.example.entities.User;
import com.example.service.ServiceChat;
import com.example.service.ServiceMessage;
import com.example.service.ServiceUser;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class ChatController {
    public final ServiceChat chatService;
    public final ServiceUser userService;
    private final SimpMessagingTemplate messagingTemplate;
    private final ServiceMessage messageService;

    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody CreateChatDto createChatDto) {
        User user = userService.findByLogin(SecurityContextHolder.getContext().getAuthentication().getName()).orElseThrow(() -> new NullPointerException());
        Integer userId = user.getId();
        return chatService.create(createChatDto, userId);
    }

    @DeleteMapping("/deleteChat/{chatId}")
    public ResponseEntity<?> delete(@PathVariable Integer chatId) {
        User user = userService.findByLogin(SecurityContextHolder.getContext().getAuthentication().getName()).orElseThrow(() -> new NullPointerException());
        Integer userId = user.getId();
        return chatService.deleteChat(chatId, userId);
    }

    @GetMapping("/getChatsId")
    public List<Integer> getChatsId() {
        User user = userService.findByLogin(SecurityContextHolder.getContext().getAuthentication().getName()).orElseThrow(() -> new NullPointerException());
        return chatService.getChatsId(user);
    }

}