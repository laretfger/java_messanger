package com.example.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.entities.Role;


@Repository
public interface RepositoryRole extends CrudRepository<Role, Integer> {
    Optional<Role> findByName(String name);
    
}