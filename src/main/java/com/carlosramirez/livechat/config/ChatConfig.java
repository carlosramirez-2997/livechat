package com.carlosramirez.livechat.config;

import com.carlosramirez.livechat.event.SocketEventListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Description;

@Configuration
public class ChatConfig {

    @Bean
    @Description("Tracks user presence (join / leave) and broacasts it to all connected users")
    public SocketEventListener presenceEventListener() {
        return new SocketEventListener();
    }
}
