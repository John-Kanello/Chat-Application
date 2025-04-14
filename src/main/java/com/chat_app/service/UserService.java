package com.chat_app.service;

import com.chat_app.model.dto.UserContainer;
import com.chat_app.model.dto.UserDto;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserService {
    private final Map<String, List<UserContainer>> userToContainer = new HashMap<>();
    private final Map<String, String> userToConnectedChat = new HashMap<>();

    public List<UserContainer> findContainerForUser(String username) {
        return userToContainer.get(username);
    }

    public void save(UserDto user) {
        userToContainer.putIfAbsent(user.sender(), new ArrayList<>());
        for(String username : userToConnectedChat.keySet()) {
            userToContainer.get(username).add(new UserContainer(user.sender(), 0L));
            userToContainer.get(user.sender()).add(new UserContainer(username, 0L));
        }
        userToConnectedChat.put(user.sender(), user.receiver());
    }

    public void placeReceiverFirst(String sender, String receiver) {
        List<UserContainer> unreadMessages = userToContainer.get(sender);
        if(unreadMessages.getFirst().getUsername().equals(receiver)) {
            return;
        }
        UserContainer unreadMessage = unreadMessages.stream()
                .filter(unreadMsg -> unreadMsg.getUsername().equals(receiver))
                .findFirst()
                .orElseThrow();
        unreadMessages.remove(unreadMessage);
        unreadMessages.addFirst(unreadMessage);
    }

    public void updateUnreadMessagesCount(String sender, String receiver) {
        List<UserContainer> unreadMessages = userToContainer.get(receiver);
        UserContainer unreadMessage = unreadMessages.stream()
                .filter(unreadMsg -> unreadMsg.getUsername().equals(sender))
                .findFirst()
                .orElseThrow();
        unreadMessages.remove(unreadMessage);
        unreadMessage.setUnreadMessageCount(unreadMessage.getUnreadMessageCount() + 1);
        unreadMessages.addFirst(unreadMessage);
    }

    public void resetUnreadMessagesCount(String currentUser, String userToConnectWith) {
        List<UserContainer> unreadMessages = userToContainer.get(currentUser);
        UserContainer unreadMessage = unreadMessages.stream()
                .filter(unreadMsg -> unreadMsg.getUsername().equals(userToConnectWith))
                .findFirst()
                .orElseThrow();
        unreadMessage.setUnreadMessageCount(0);
        userToConnectedChat.put(currentUser, userToConnectWith);
    }

    public void delete(String userToDelete) {
        userToConnectedChat.remove(userToDelete);
        userToContainer.remove(userToDelete);
        for(String username : userToConnectedChat.keySet()) {
            userToContainer.get(username)
                 .removeIf(unreadMessage -> unreadMessage.getUsername().equals(userToDelete));
        }
    }

    public String getConnectedChatForUser(String user) {
        return userToConnectedChat.get(user);
    }

    public void putConnectedChatForUser(String user, String userToConnectWith) {
        userToConnectedChat.put(user, userToConnectWith);
    }

    public Set<String> getConnectedUsers() {
        return userToConnectedChat.keySet();
    }
}
