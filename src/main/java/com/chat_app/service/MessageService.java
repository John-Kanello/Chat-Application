package com.chat_app.service;

import com.chat_app.model.entity.Message;
import com.chat_app.repository.MessageRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageService {
    private final MessageRepository messageRepository;

    public MessageService(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    public List<Message> findAll() {
        return messageRepository.findAll();
    }

    public Message save(Message message) {
        return messageRepository.save(message);
    }
}
