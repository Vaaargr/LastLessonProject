package com.lastlesson.repository;

import com.lastlesson.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Integer> {
    public List<Message> findByUsername(String username);
}