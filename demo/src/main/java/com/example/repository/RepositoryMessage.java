package com.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.entities.Message;

public interface RepositoryMessage extends JpaRepository<Message, Integer> {

}
