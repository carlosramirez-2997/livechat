package com.carlosramirez.livechat.services.message;

import com.carlosramirez.livechat.model.dto.websocket.ChatMessageDTO;

public interface MessageProcessor {
    ChatMessageDTO process(ChatMessageDTO message, String room);
}
