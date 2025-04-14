package com.chat_app.controller.rest;

import com.chat_app.model.dto.UserContainer;
import com.chat_app.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/container")
public class UserContainerController {
    private final UserService userService;

    public UserContainerController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<UserContainer>> findAll(@RequestParam("username") String username) {
        return ResponseEntity.ok()
                .body(userService.findContainerForUser(username));
    }

    @PutMapping("/reset")
    public ResponseEntity<List<UserContainer>> resetUnreadMessagesCounter(@RequestParam("currentUser") String currentUser,
                                                                          @RequestParam("userToConnectWith") String userToConnectWith) {
        String connectedChatForUser = userService.getConnectedChatForUser(currentUser);
        if(!connectedChatForUser.equals(userToConnectWith)) {
            userService.resetUnreadMessagesCount(currentUser, userToConnectWith);
        }
        return ResponseEntity.ok()
                .body(userService.findContainerForUser(currentUser));
    }

    @PutMapping("/connect")
    public ResponseEntity<String> connectUsers(@RequestParam("currentUser") String currentUser,
                                               @RequestParam("userToConnectWith") String userToConnectWith) {
        userService.putConnectedChatForUser(currentUser, userToConnectWith);
        return ResponseEntity.ok()
                .build();
    }
}
