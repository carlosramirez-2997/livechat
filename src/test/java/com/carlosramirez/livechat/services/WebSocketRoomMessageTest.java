package com.carlosramirez.livechat.services;


import com.carlosramirez.livechat.model.dto.websocket.ChatMessageDTO;
import com.carlosramirez.livechat.services.message.MessageProcessor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;

import java.lang.reflect.Type;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class WebSocketRoomMessageTest {

    @LocalServerPort
    private int port;

    WebSocketStompClient stompClient;

    @Mock
    private MessageProcessor messageProcessor;

    @BeforeEach
    public void setup() {
        stompClient = new WebSocketStompClient(new StandardWebSocketClient());
        stompClient.setMessageConverter(new MappingJackson2MessageConverter());
    }

    @Test
    public void testWebSocketMessageExchange() throws Exception {
        String WEBSOCKET_URI = "ws://localhost:" + port + "/ws";

        ChatMessageDTO messageToSend = new ChatMessageDTO("john", "Hello everyone!");
        Mockito.when(messageProcessor.process(Mockito.any(), Mockito.eq("1"))).thenReturn(messageToSend);

        DefaultStompFrameHandler handler1 = new DefaultStompFrameHandler();
        StompSession session = stompClient.connect(WEBSOCKET_URI, handler1).get(1, TimeUnit.SECONDS);
        session.subscribe("/topic/room/1", handler1);

        session.send("/app/chat/1", messageToSend);

        ChatMessageDTO received = handler1.getBlockingQueue().poll(15, TimeUnit.SECONDS);
        assertNotNull(received);
        assertEquals("john", received.getUsername());
        assertEquals("Hello everyone!", received.getMessage());
    }

    @Test
    public void testUserMessageReceivedByRoom() throws Exception {
        String WEBSOCKET_URI = "ws://localhost:" + port + "/ws";

        ChatMessageDTO messageToSend = new ChatMessageDTO("john", "Hello everyone!");
        Mockito.when(messageProcessor.process(Mockito.any(), Mockito.eq("1"))).thenReturn(messageToSend);

        DefaultStompFrameHandler handler1 = new DefaultStompFrameHandler();
        StompSession session1 = stompClient.connect(WEBSOCKET_URI,  handler1).get(1, TimeUnit.SECONDS);
        session1.subscribe("/topic/room/1", handler1);

        DefaultStompFrameHandler handler2 = new DefaultStompFrameHandler();
        StompSession session2 = stompClient.connect(WEBSOCKET_URI, handler2).get(1, TimeUnit.SECONDS);
        session2.subscribe("/topic/room/2", handler2);

        session1.send("/app/chat/1", messageToSend);

        ChatMessageDTO received = handler1.getBlockingQueue().poll(2, TimeUnit.SECONDS);
        assertNotNull(received);

        ChatMessageDTO shouldBeNull = handler2.getBlockingQueue().poll(2, TimeUnit.SECONDS);
        assertNull(shouldBeNull, "No message should be received in other room");
    }

    class DefaultStompFrameHandler extends StompSessionHandlerAdapter {

        private final BlockingQueue<ChatMessageDTO> blockingQueue;

        public DefaultStompFrameHandler() {
            this.blockingQueue = new ArrayBlockingQueue<>(2);
        }

        BlockingQueue<ChatMessageDTO> getBlockingQueue() {
            return this.blockingQueue;
        }

        @Override
        public Type getPayloadType(StompHeaders stompHeaders) {
            return ChatMessageDTO.class;
        }

        @Override
        public void handleFrame(StompHeaders stompHeaders, Object o) {
            this.blockingQueue.offer((ChatMessageDTO) o);
        }

        @Override
        public void handleException(StompSession session, StompCommand command, StompHeaders headers, byte[] payload, Throwable exception) {
            exception.printStackTrace();
        }
    }

}