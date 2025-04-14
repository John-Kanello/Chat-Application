package com.chat_app.model.dto;

import java.time.LocalDateTime;

public record MessageDto(
   String sender,
   LocalDateTime timestamp,
   String content
) {}
