package com.chat_app.service;

import com.chat_app.model.entity.PrivateMessage;
import com.chat_app.repository.PrivateMessageRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PrivateMessageService {
    private final PrivateMessageRepository privateMessageRepository;

    public PrivateMessageService(PrivateMessageRepository privateMessageRepository) {
        this.privateMessageRepository = privateMessageRepository;
    }

    public List<PrivateMessage> findAllForSenderAndReceiver(String sender, String receiver) {
        return privateMessageRepository.findAllForSenderAndReceiver(sender, receiver);
    }

    public PrivateMessage save(PrivateMessage privateMessage) {
        return privateMessageRepository.save(privateMessage);
    }
}
