package com.example.service;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.dto.CreateUserDto;
import com.example.dto.UpdateUserDto;
import com.example.entities.User;
import com.example.jwtUtils.JwtTokenUtility;
import com.example.repository.RepositoryRole;
import com.example.repository.RepositoryUser;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ServiceUser implements UserDetailsService {

    private final RepositoryUser repositoryUser;
    private final RepositoryRole repositoryRole;

    public Optional<User> findByLogin(String login) {
        return repositoryUser.findByLogin(login);
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException{
        User user = findByLogin(userName).orElseThrow(() -> new UsernameNotFoundException(
            String.format("Пользователь с именем '%s' не найден", userName)
        ));
        return new org.springframework.security.core.userdetails.User(
            user.getLogin(),
            user.getPassword(),
            user.getRoles().stream().map(role -> new SimpleGrantedAuthority(role.getName().toString())).collect(Collectors.toSet())
        );
    }

    public void createNewUser(User user) {
        repositoryUser.save(user);
    }

    public void updateUserData(UpdateUserDto userDto) { 
        User user = findByLogin(userDto.getLogin()).orElseThrow(() -> new UsernameNotFoundException(
            String.format("Пользователь с именем '%s' не найден", userDto.getLogin())
        ));
        user.setLogin(userDto.getLogin());
        user.setPassword(userDto.getPassword());
        user.setRoles(Set.of(repositoryRole.findByName("ROLE_USER").get()));
        repositoryUser.save(user);
    }

    public boolean hasRole(String userName, String role) { 
        if(loadUserByUsername(userName).equals(role)) return true;
        else return false;
    }
     

    // @GetMapping("/findAll")
    // public List<User> findAll() {
    //     return UserRepository.findAll();
    // }

    // @GetMapping("/findOne{id}")
    // public User findOne(@PathVariable Integer id) {
    //     Optional<User> userOp = UserRepository.findById(id);
    //     if(userOp.isPresent()) return userOp.get();
    //     else return new User();
    // }

    // @DeleteMapping("/delete{id}")
    // public String delete(@PathVariable Integer id) {
    //     try{
    //         UserRepository.deleteById(id);
    //         return "Успешно удалено!";
    //     } catch(Exception e) {
    //         System.out.println(e);
    //         return "Не удалено";
    //     }
    // }

    // @PutMapping("/update{id}")
    // public String update(@RequestBody UpdateUserDto userDto, @PathVariable Integer id ) {
    //     try{
    //         User user = new User();
    //         user.setLogin(userDto.getLogin());
    //         user.setPassword(userDto.getPassword());
            
    //         UserRepository.save(user);
    //         return "Успешно!";
    //     } catch(Exception e) {
    //         System.out.println(e);
    //         return "Не успешно";
    //     }

    // }

}
