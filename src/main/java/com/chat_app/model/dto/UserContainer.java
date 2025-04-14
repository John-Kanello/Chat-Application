package com.chat_app.model.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@EqualsAndHashCode
@ToString
public class UserContainer {
    private String username;
    private long unreadMessageCount;

    public UserContainer(String username, long unreadMessageCount) {
        this.username = username;
        this.unreadMessageCount = unreadMessageCount;
    }
}