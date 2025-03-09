package com.example.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.dto.CreateUserDto;
import com.example.dto.GetUserDto;
import com.example.dto.GetUserInfoDto;
import com.example.dto.JwtRequest;
import com.example.entities.User;
import com.example.service.ServiceAuth;
import com.example.service.ServiceUser;

import lombok.RequiredArgsConstructor;



@RestController
@RequiredArgsConstructor
public class AuthController {
    
    private final ServiceUser userService;
    private final ServiceAuth authService;

    @PostMapping("/registry")
    public ResponseEntity<?> registry(@RequestBody CreateUserDto userDto) { 
        return authService.registry(userDto);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody JwtRequest request) { 
        System.out.println(request.getLogin() + request.getPassword());
        return authService.login(request);
    }

    @GetMapping("/me")
    public ResponseEntity<?> me() { 
        User user = userService.findByLogin(SecurityContextHolder.getContext().getAuthentication().getName()).orElseThrow(() -> new NullPointerException());
        GetUserInfoDto userInfo = new GetUserInfoDto(user.getEmail(), user.getLogin());
        System.out.println(user.getEmail() + " " + user.getLogin());

        return ResponseEntity.ok(userInfo);
        
    }
}