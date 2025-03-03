package com.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.entities.Chat;

@Repository
public interface RepositoryChat extends JpaRepository<Chat, Integer>  {

}