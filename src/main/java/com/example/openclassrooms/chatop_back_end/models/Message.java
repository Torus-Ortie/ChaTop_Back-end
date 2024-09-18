package com.example.openclassrooms.chatop_back_end.models;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "messages")
public class Message {
    
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="rental_id")
    private Rental rental;

    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;

    @Column(name="message")
    private String message;

    @Column(name="created_at")
    @CreationTimestamp
    private LocalDateTime created_at;

    @Column(name="updated_at")
    @UpdateTimestamp
    private LocalDateTime updated_at;
}
