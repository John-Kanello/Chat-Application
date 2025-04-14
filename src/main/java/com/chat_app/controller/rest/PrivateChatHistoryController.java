package com.chat_app.controller.rest;

import com.chat_app.model.dto.PrivateMessageDto;
import com.chat_app.service.PrivateMessageService;
import com.chat_app.util.PrivateMessageMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class PrivateChatHistoryController {
    private final PrivateMessageService privateMessageService;
    private final PrivateMessageMapper privateMessageMapper;

    public PrivateChatHistoryController(PrivateMessageService privateMessageService,
                                        PrivateMessageMapper privateMessageMapper) {
        this.privateMessageService = privateMessageService;
        this.privateMessageMapper = privateMessageMapper;
    }

    @GetMapping("/chat/private/history")
    public ResponseEntity<List<PrivateMessageDto>> getChatHistory(@RequestParam("sender") String sender,
                                                                  @RequestParam("receiver") String receiver) {
        return ResponseEntity.ok()
                .body(privateMessageService.findAllForSenderAndReceiver(sender, receiver).stream()
                        .map(privateMessageMapper::toResponseDto)
                        .toList());
    }
}
