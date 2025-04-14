package com.chat_app.controller.websocket;

import com.chat_app.model.dto.MessageDto;
import com.chat_app.model.dto.PrivateMessageDto;
import com.chat_app.service.MessageService;
import com.chat_app.service.PrivateMessageService;
import com.chat_app.service.UserService;
import com.chat_app.util.MessageMapper;
import com.chat_app.util.PrivateMessageMapper;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Controller;

@Controller
public class ChatController {
    private final MessageService messageService;
    private final MessageMapper messageMapper;
    private final PrivateMessageService privateMessageService;
    private final PrivateMessageMapper privateMessageMapper;
    private final UserService userService;
    private final SimpMessageSendingOperations messagingTemplate;

    public ChatController(MessageService messageService,
                          MessageMapper messageMapper,
                          PrivateMessageService privateMessageService,
                          PrivateMessageMapper privateMessageMapper,
                          UserService userService,
                          SimpMessageSendingOperations messagingTemplate) {
        this.messageService = messageService;
        this.messageMapper = messageMapper;
        this.privateMessageService = privateMessageService;
        this.privateMessageMapper = privateMessageMapper;
        this.userService = userService;
        this.messagingTemplate = messagingTemplate;
    }

    @MessageMapping("/message")
    @SendTo("/topic/message")
    public MessageDto handleMessage(MessageDto message) {
        messageService.save(messageMapper.toEntity(message));
        return message;
    }

    @MessageMapping("/message/private")
    public void handlePrivateMessage(PrivateMessageDto message) {
        privateMessageService.save(privateMessageMapper.toEntity(message));
        String connectedChatForReceiver = userService.getConnectedChatForUser(message.receiver());
        if(!connectedChatForReceiver.equals(message.sender())) {
            userService.updateUnreadMessagesCount(message.sender(), message.receiver());
        }
        userService.placeReceiverFirst(message.sender(), message.receiver());
        messagingTemplate.convertAndSend("/user/" + message.sender() + "/queue/container", userService.findContainerForUser(message.sender()));
        messagingTemplate.convertAndSend("/user/" + message.receiver() + "/queue/container", userService.findContainerForUser(message.receiver()));
        messagingTemplate.convertAndSend("/user/" + message.sender() + "/queue/messages", message);
        messagingTemplate.convertAndSend("/user/" + message.receiver() + "/queue/messages", message);
    }
}
