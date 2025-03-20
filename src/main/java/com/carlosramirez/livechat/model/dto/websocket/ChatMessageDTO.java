package com.carlosramirez.livechat.model.dto.websocket;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ChatMessageDTO {

    @JsonProperty(value = "sender_id")
    private String senderId;

    @JsonProperty(value = "message")
    private String message;
}