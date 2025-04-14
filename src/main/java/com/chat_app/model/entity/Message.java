package com.chat_app.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
@Entity
@Table(name = "messages")
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String sender;
    private LocalDateTime timestamp;
    private String content;

    public Message(String sender, LocalDateTime timestamp, String content) {
        this.sender = sender;
        this.timestamp = timestamp;
        this.content = content;
    }
}
