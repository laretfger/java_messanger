package com.example.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.dto.AddNewUserDto;
import com.example.dto.CreateChatDto;
import com.example.dto.GetChatDto;
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

    @GetMapping("/getChats")
    public List<GetChatDto> getChats() {
        User user = userService.findByLogin(SecurityContextHolder.getContext().getAuthentication().getName()).orElseThrow(() -> new NullPointerException());
        System.out.println("start");
        return chatService.getChats(user);
    }

    @PostMapping("/addNewUser")
    public ResponseEntity<?> addNewUser(@RequestBody AddNewUserDto addNewUserDto) {
        User user = userService.findByLogin(SecurityContextHolder.getContext().getAuthentication().getName()).orElseThrow(() -> new NullPointerException());
        Integer userId = user.getId();
        User newUser = userService.findByLogin(addNewUserDto.getLoginNewUser()).orElseThrow(() -> new NullPointerException());
        return chatService.addNewUser(userId, newUser.getId(), addNewUserDto.getChatId());
    }
}