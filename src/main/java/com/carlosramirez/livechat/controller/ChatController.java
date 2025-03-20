package com.carlosramirez.livechat.controller;

import com.carlosramirez.livechat.model.dto.websocket.ChatMessageDTO;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class ChatController {

    @MessageMapping("/chat/{roomId}")
    @SendTo("/topic/room/{roomId}")
    public ChatMessageDTO sendMessage(@Payload ChatMessageDTO message, @DestinationVariable String roomId) {
        System.out.println(message);
        return message;
    }
}
