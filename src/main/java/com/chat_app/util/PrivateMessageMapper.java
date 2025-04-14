package com.chat_app.util;

import com.chat_app.model.dto.PrivateMessageDto;
import com.chat_app.model.entity.PrivateMessage;
import org.springframework.stereotype.Component;

@Component
public class PrivateMessageMapper implements RequestMapper<PrivateMessage, PrivateMessageDto>, ResponseMapper<PrivateMessage, PrivateMessageDto>{

    @Override
    public PrivateMessage toEntity(PrivateMessageDto requestDto) {
        return new PrivateMessage(
                requestDto.sender(),
                requestDto.receiver(),
                requestDto.content(),
                requestDto.timestamp()
        );
    }

    @Override
    public PrivateMessageDto toResponseDto(PrivateMessage entity) {
        return new PrivateMessageDto(
                entity.getSender(),
                entity.getReceiver(),
                entity.getContent(),
                entity.getTimestamp()
        );
    }
}
