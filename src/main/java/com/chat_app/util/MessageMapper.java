package com.chat_app.util;

import com.chat_app.model.dto.MessageDto;
import com.chat_app.model.entity.Message;
import org.springframework.stereotype.Component;

@Component
public class MessageMapper implements RequestMapper<Message, MessageDto>, ResponseMapper<Message, MessageDto> {
    @Override
    public Message toEntity(MessageDto requestDto) {
        return new Message(
                requestDto.sender(),
                requestDto.timestamp(),
                requestDto.content()
        );
    }

    @Override
    public MessageDto toResponseDto(Message entity) {
        return new MessageDto(
                entity.getSender(),
                entity.getTimestamp(),
                entity.getContent()
        );
    }
}
