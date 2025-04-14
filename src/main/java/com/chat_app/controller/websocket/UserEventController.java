package com.chat_app.controller.websocket;

import com.chat_app.model.dto.UserDto;
import com.chat_app.service.UserService;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Controller;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import java.util.Objects;

@Controller
public class UserEventController {
    private final UserService userService;
    private final SimpMessageSendingOperations messagingTemplate;

    public UserEventController(UserService userService, SimpMessageSendingOperations messagingTemplate) {
        this.userService = userService;
        this.messagingTemplate = messagingTemplate;
    }

    @MessageMapping("user/new")
    public void handleNewUser(UserDto userDto, SimpMessageHeaderAccessor headerAccessor) {
        userService.save(userDto);
        Objects.requireNonNull(headerAccessor.getSessionAttributes()).put("username", userDto.sender());
        for(String username : userService.getConnectedUsers()) {
            messagingTemplate.convertAndSend("/topic/user/" + username + "/new", userService.findContainerForUser(username));
        }
    }

    @EventListener
    public void handleUserLeave(SessionDisconnectEvent event) {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
        String username = (String) Objects.requireNonNull(headerAccessor.getSessionAttributes()).get("username");
        if(username != null) {
            userService.delete(username);
            for(String user : userService.getConnectedUsers()) {
                messagingTemplate.convertAndSend("/topic/user/" + user + "/leave", userService.findContainerForUser(user));
            }
        }
    }
}
