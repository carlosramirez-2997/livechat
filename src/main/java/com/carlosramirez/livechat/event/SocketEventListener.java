package com.carlosramirez.livechat.event;

import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.web.socket.messaging.SessionSubscribeEvent;

import java.util.Optional;

public class SocketEventListener {

    @EventListener
    public void handleSubscription(SessionSubscribeEvent event) {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());

        String user = Optional.of(headerAccessor)
                .map(h -> h.getFirstNativeHeader("user"))
                .orElseThrow(() -> new IllegalArgumentException("Invalid subscription: User header is required"));

        System.out.println("User subscribed: " + user);
    }
}