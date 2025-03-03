package com.example.service;

import java.util.Set;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import com.example.dto.CreateUserDto;
import com.example.dto.GetUserDto;
import com.example.dto.JwtRequest;
import com.example.entities.User;
import com.example.error.AuthError;
import com.example.jwtUtils.JwtTokenUtility;
import com.example.repository.RepositoryRole;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ServiceAuth {

    private final JwtTokenUtility jwtTokenUtility;
    public final ServiceUser serviceUser;
    private final AuthenticationManager authenticationManager;
    private final RepositoryRole repositoryRole;
    private final PasswordEncoder passwordEncoder;

    public ResponseEntity<?> registry(CreateUserDto userDto) { 
        System.out.println("registry");
        if(userDto.getPassword() != null && userDto.getConfirm_password() != null && userDto.getPassword().equals(userDto.getConfirm_password())) {
            System.out.println(serviceUser.findByLogin(userDto.getLogin()).isPresent());

            if(serviceUser.findByLogin(userDto.getLogin()).isPresent()) {
                return new ResponseEntity(new AuthError("Пользователь с таким именем уже существует", HttpStatus.UNAUTHORIZED.value()), HttpStatus.UNAUTHORIZED);
            }

            System.out.println("registry user Dto");
            System.out.println("new User");
            User user = new User();
            user.setLogin(userDto.getLogin());
            System.out.println("new User update");
            user.setPassword(passwordEncoder.encode(userDto.getPassword()));
            
            System.out.println(repositoryRole.findByName("ROLE_USER").get());
            user.setRoles(Set.of(repositoryRole.findByName("ROLE_USER").get()));

            System.out.println(user.getRoles());
            serviceUser.createNewUser(user);
            Integer userId = serviceUser.findByLogin(userDto.getLogin()).get().getId();
            System.out.println("registry final");
            UserDetails userDetails = serviceUser.loadUserByUsername(userDto.getLogin());
            System.out.println("registry userDetails " + userDetails);
            String token = jwtTokenUtility.generateToken(userDetails);
            GetUserDto getUserDto = new GetUserDto(userDto.getLogin(), userId, token);
            return ResponseEntity.ok(getUserDto);
        }
        else return new ResponseEntity(new AuthError("Пароль и подтверждение пароля не совпадают", HttpStatus.UNAUTHORIZED.value()), HttpStatus.UNAUTHORIZED);
    }

    public ResponseEntity<?> login(JwtRequest request) { 
        try{
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getLogin(), request.getPassword()));
            UserDetails userDetails = serviceUser.loadUserByUsername(request.getLogin());
            String token = jwtTokenUtility.generateToken(userDetails);
            Integer userId = serviceUser.findByLogin(request.getLogin()).get().getId();
            GetUserDto getUserDto = new GetUserDto(request.getLogin(), userId, token);
            return ResponseEntity.ok(getUserDto);
        } catch (BadCredentialsException err) {
            return new ResponseEntity<>(new AuthError("Некорректный логин или пароль", HttpStatus.UNAUTHORIZED.value()), HttpStatus.UNAUTHORIZED);
        }
        
    }
}