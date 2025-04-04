package com.carlosramirez.livechat.services.chatroom.impl;

import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

@Service
public class ActiveUserService {

    private final SimpMessagingTemplate messagingTemplate;
    private final Queue<String> activeUsers = new ConcurrentLinkedQueue<>();

    public ActiveUserService(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    public void registerUser(String username) {
        if (activeUsers.contains(username)) {
            messagingTemplate.convertAndSendToUser(username, "/queue/disconnect", "You have been disconnected due to a new login.");
        }
        activeUsers.add(username);
    }

    public void removeUser(String username) {
        activeUsers.remove(username);
    }
}
