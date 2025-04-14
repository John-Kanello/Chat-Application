package com.chat_app.controller.rest;

import com.chat_app.model.dto.MessageDto;
import com.chat_app.service.MessageService;
import com.chat_app.util.MessageMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ChatHistoryController {
    private final MessageService messageService;
    private final MessageMapper messageMapper;

    public ChatHistoryController(MessageService messageService, MessageMapper messageMapper) {
        this.messageService = messageService;
        this.messageMapper = messageMapper;
    }

    @GetMapping("/chat/history")
    public ResponseEntity<List<MessageDto>> getChatHistory() {
        return ResponseEntity.ok()
                .body(messageService.findAll().stream()
                        .map(messageMapper::toResponseDto)
                        .toList()
                );
    }
}
