package com.carlosramirez.livechat.config;

import com.carlosramirez.livechat.services.chatroom.impl.ActiveUserService;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.web.socket.messaging.SessionConnectEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

@Configuration
public class SocketEventListener {

    private final ActiveUserService activeUserService;

    public SocketEventListener(ActiveUserService activeUserService) {
        this.activeUserService = activeUserService;
    }

    @EventListener
    public void handleWebSocketConnectListener(SessionConnectEvent event) {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
        String username = headerAccessor.getFirstNativeHeader("username");

        if (username != null) {
            activeUserService.registerUser(username);
        }
    }

    @EventListener
    public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
        String sessionId = headerAccessor.getSessionId();
        activeUserService.removeUser(sessionId);
    }
}