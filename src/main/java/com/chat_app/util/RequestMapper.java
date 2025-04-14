package com.chat_app.util;
public interface RequestMapper<E,R> {
    E toEntity(R requestDto);
}

