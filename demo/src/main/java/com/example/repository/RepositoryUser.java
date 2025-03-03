package com.example.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.entities.User;


@Repository
public interface RepositoryUser extends JpaRepository<User, Integer> {
    Optional<User> findByLogin(String login);
}