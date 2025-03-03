package com.example.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.dto.CreateChatDto;
import com.example.dto.GetChatDto;
import com.example.entities.Chat;
import com.example.entities.User;
import com.example.error.AuthError;
import com.example.repository.RepositoryChat;
import com.example.repository.RepositoryUser;

import lombok.RequiredArgsConstructor;



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

        Chat chat = new Chat(); 
        List<Integer> usersId = new ArrayList<>();
        usersId.add(userId);
        chat.setUsersId(usersId);
        chat.setChatName(createChatDto.getChatName());
        chatRepository.save(chat);
        System.out.println("chatId " + chat.getChatId());
        GetChatDto dto = new GetChatDto(chat.getChatId(), chat.getChatName());
        User user = userRepository.findById(userId).get();
        List<Integer> id = user.getChatsId();
        id.add(chat.getChatId());
        System.out.println("ids " + id);
        user.setChatsId(id);
        userRepository.save(user);
        System.out.println("idsUser " + user.getChatsId());
        return ResponseEntity.ok(dto);
    }

    public ResponseEntity<?> addNewUser(Integer userId, Integer idNewUser, Integer chatId) {
        Boolean sucsess = checkSucsess(chatId, userId);
        if(!sucsess) return new ResponseEntity(new AuthError("Пользователь не состоит в этом чате", HttpStatus.UNAUTHORIZED.value()), HttpStatus.UNAUTHORIZED);
        
        Chat chat = chatRepository.findById(chatId).get();
        List<Integer> usersId = chat.getUsersId();
        usersId.add(idNewUser);
        chat.setUsersId(usersId);

        return ResponseEntity.ok("Новый пользователь успешно добавлен");
    }

    public List<GetChatDto> getChats(User user) {
        List<Integer> chatsId = user.getChatsId();
        System.out.println("idsUser " + user.getChatsId());
        if(chatsId == null) return new ArrayList<GetChatDto>();
        List<GetChatDto> Chatsdto = new ArrayList();
        for(Integer chatId : chatsId) {
            Chat chat = chatRepository.findById(chatId).get();
            Chatsdto.add(new GetChatDto(chatId, chat.getChatName()));
        }
        return Chatsdto;
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



