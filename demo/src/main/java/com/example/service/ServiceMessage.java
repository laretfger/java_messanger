package com.example.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.dto.CreateMessageDto;
import com.example.dto.GetMessageDto;
import com.example.dto.UpdateMessageDto;
import com.example.entities.Chat;
import com.example.entities.Message;
import com.example.error.AuthError;
import com.example.repository.RepositoryChat;
import com.example.repository.RepositoryMessage;

import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class ServiceMessage {


    public final RepositoryMessage messageRepository;
    public final RepositoryChat chatRepository;

    private Boolean checkSucsess(Integer chatId, Integer userId) throws NullPointerException {
        Chat chat = chatRepository.findById(chatId).orElseThrow(() -> new NullPointerException());
        for(Integer chatUserId : chat.getUsersId()) {
            if(chatUserId == userId) return true;
        }
        return false;
    }

    public Message create(CreateMessageDto createMessageDto, Integer userId) throws NullPointerException {
        Boolean sucsess = checkSucsess(createMessageDto.getChatId(), userId);
        if(!sucsess) throw new NullPointerException();

        Message message = new Message();
        message.setData(createMessageDto.getData());
        message.setDate(LocalDate.now());
        message.setOwnerUserId(userId);
        message.setChatId(createMessageDto.getChatId());
        messageRepository.save(message);
        return message;
    }

    public ResponseEntity<?> getAllMessage(Integer chatId, Integer userId) {
        Boolean sucsess = checkSucsess(chatId, userId);
        if(!sucsess) return new ResponseEntity(new AuthError("Пользователь не состоит в этом чате", HttpStatus.UNAUTHORIZED.value()), HttpStatus.UNAUTHORIZED);

        List<Message> messages = messageRepository.findAll();
        List<GetMessageDto> messagesGet = new ArrayList();
        for(Message message : messages) {
            if(message.getChatId() == chatId) messagesGet.add(new GetMessageDto(message.getMessageId(), message.getData(), message.getOwnerUserId()));
        }
        return ResponseEntity.ok(messagesGet);
    }

    public ResponseEntity<?> update(Integer userId, UpdateMessageDto updateMessageDto) throws NullPointerException {
        Boolean sucsess = checkSucsess(updateMessageDto.getChatId(), userId);
        if(!sucsess) return new ResponseEntity(new AuthError("Пользователь не состоит в этом чате", HttpStatus.UNAUTHORIZED.value()), HttpStatus.UNAUTHORIZED);;

        Message message = messageRepository.findById(updateMessageDto.getMessageId()).orElseThrow(() -> new NullPointerException());
        message.setData(updateMessageDto.getNewData());
        messageRepository.save(message);
        return ResponseEntity.ok("Обновленно");
    }

    public ResponseEntity<?> delete(Integer chatId, Integer userId, Integer messageId) {
        Boolean sucsess = checkSucsess(chatId, userId);
        if(!sucsess) return new ResponseEntity(new AuthError("Пользователь не состоит в этом чате", HttpStatus.UNAUTHORIZED.value()), HttpStatus.UNAUTHORIZED); ;

        messageRepository.deleteById(messageId);
        return ResponseEntity.ok("Удалено");
    }
}