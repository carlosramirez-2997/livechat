package com.carlosramirez.livechat.model.dto.websocket;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChatMessageDTO {

    @JsonProperty(value = "username")
    private String username;

    @JsonProperty(value = "message")
    private String message;
}