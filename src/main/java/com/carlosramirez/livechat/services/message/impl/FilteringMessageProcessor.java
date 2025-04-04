package com.carlosramirez.livechat.services.message.impl;

import com.carlosramirez.livechat.model.dto.websocket.ChatMessageDTO;
import com.carlosramirez.livechat.services.message.MessageProcessor;
import org.springframework.stereotype.Service;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.function.Function;

@Service
public class FilteringMessageProcessor implements MessageProcessor {

    private static String BASE_PATH = "src/main/resources/message_logs/";

    private static final List<String> BANNED_WORDS = List.of("badword", "spam");

    @Override
    public ChatMessageDTO process(ChatMessageDTO message, String room) {
        return ((Function<ChatMessageDTO, ChatMessageDTO>) this::validateMessageContent)
                .andThen(msg -> saveMessageContentByRoom(msg, room))
                .apply(message);
    }

    private ChatMessageDTO validateMessageContent(ChatMessageDTO message) {
        String filteredContent = message.getMessage();
        for (String word : BANNED_WORDS) {
            filteredContent = filteredContent.replaceAll("(?i)" + word, "***");
        }
        message.setMessage(filteredContent);
        return message;
    }

    private ChatMessageDTO saveMessageContentByRoom(ChatMessageDTO message, String room) {
        File directory = new File(BASE_PATH);
        if (!directory.exists()) {
            directory.mkdirs();
        }

        File file = new File(BASE_PATH + room + "_messages.txt");

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, true))) {
            writer.write(message.getUsername() + ": " + message.getMessage());
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return message;
    }
}
