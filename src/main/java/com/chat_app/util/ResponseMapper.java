package com.chat_app.util;

public interface ResponseMapper<E,R> {
    R toResponseDto(E entity);
}
