package com.example.service;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.example.repository.RepositoryChat;
import com.example.repository.RepositoryUser;
import com.example.dto.CreateChatDto;
import com.example.entities.Chat;
import lombok.RequiredArgsConstructor;
import com.example.entities.User;
import com.example.error.AuthError;



@Service
@RequiredArgsConstructor
public class ServiceChat {

    public final RepositoryChat chatRepository;
    public final RepositoryUser userRepository;

    private Boolean checkSucsess(Integer chatId, Integer userId) throws NullPointerException {
        Chat chat = chatRepository.findById(chatId).orElseThrow(() -> new NullPointerException());
        for(Integer chatUserId : chat.getUsersId()) {
            if(chatUserId == userId) return true;
        }
        return false;
    }

    public ResponseEntity<?> create(CreateChatDto createChatDto, Integer userId) {
        for(Integer chatUserId : createChatDto.getUsersId()) {
            if(chatUserId != userId) return new ResponseEntity(new AuthError("Пользователь не состоит в этом чате", HttpStatus.UNAUTHORIZED.value()), HttpStatus.UNAUTHORIZED);;
        }

        Chat chat = new Chat(); 
        chat.setUsersId(createChatDto.getUsersId());
        chatRepository.save(chat);
        return ResponseEntity.ok(chat.getChatId());
    }

    public List<Integer> getChatsId(User user) {
        return user.getChatsId();
    }

    // private void connectChat(Integer userId, Chat chat) {
        
    // }

    // public void connectAllChat(Integer userId) {
    //     List<Chat> chats = chatRepository.findAll(chatId)
    //     for(Chat chat : chats) {
    //         chat.getUserId().stream().forEach(chatUserId -> {
    //             if(chatUserId == userId) {
    //                 connectChat(userId, chat);
    //             };
    //         })
    //     }
    // }

    public ResponseEntity<?> deleteChat(Integer chatId, Integer userId) {
        Boolean sucsess = checkSucsess(chatId, userId);
        if(!sucsess) return new ResponseEntity(new AuthError("Пользователь не состоит в этом чате", HttpStatus.UNAUTHORIZED.value()), HttpStatus.UNAUTHORIZED);
        chatRepository.deleteById(chatId);
        return ResponseEntity.ok("Успешно удалено");
    }
}



