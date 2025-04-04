package com.carlosramirez.livechat.controller;

import com.carlosramirez.livechat.model.dto.websocket.ChatMessageDTO;
import com.carlosramirez.livechat.services.message.MessageProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import static io.micrometer.common.util.StringUtils.isNotBlank;

@Controller
public class ChatWSController {

    @Autowired
    private MessageProcessor messageProcessor;

    public ChatWSController(MessageProcessor messageProcessor) {
        this.messageProcessor = messageProcessor;
    }

    @MessageMapping("/chat/{roomId}")
    @SendTo("/topic/room/{roomId}")
    public ChatMessageDTO sendMessage(@Payload ChatMessageDTO message, @DestinationVariable String roomId) {
        if (isNotBlank(message.getUsername()) && message.getUsername().startsWith("GUEST")) {
            throw new RuntimeException("Guest users are not allowed to send messages.");
        }
        return messageProcessor.process(message, roomId);
    }
}