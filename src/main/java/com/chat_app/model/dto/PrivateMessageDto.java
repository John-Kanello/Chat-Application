package com.chat_app.model.dto;

import java.time.LocalDateTime;

public record PrivateMessageDto(
        String sender,
        String receiver,
        String content,
        LocalDateTime timestamp
) {}
