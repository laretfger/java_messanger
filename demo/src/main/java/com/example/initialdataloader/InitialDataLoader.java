package com.example.initialdataloader;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.entities.Role;
import com.example.enums.RoleName;
import com.example.repository.RepositoryRole;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class InitialDataLoader {
    private final RepositoryRole repositoryRole;

    @Bean
    public CommandLineRunner dataLoader(RepositoryRole repository) {
        return (args) -> {
            if(repositoryRole.findByName(RoleName.ROLE_USER.toString()).isEmpty()){
                Role roleUser = new Role();
                roleUser.setName(RoleName.ROLE_USER.toString());
                repository.save(roleUser);
            }
                
            if(repositoryRole.findByName(RoleName.ROLE_WORKER.toString()).isEmpty()){
                Role roleWorker = new Role();
                roleWorker.setName(RoleName.ROLE_WORKER.toString());            
                repository.save(roleWorker);
            }

            if(repositoryRole.findByName(RoleName.ROLE_ADMIN.toString()).isEmpty()){
                Role roleAdmin = new Role();
                roleAdmin.setName(RoleName.ROLE_ADMIN.toString());
                repository.save(roleAdmin);
            }
        };
    }
} 