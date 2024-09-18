package com.example.openclassrooms.chatop_back_end.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.openclassrooms.chatop_back_end.models.Message;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {

}
