package com.carlosramirez.livechat.model.dto.rest;

import lombok.Data;
import org.springframework.http.HttpStatus;

import java.util.List;

@Data
public class Notification {

    private String status;
    private List<String> message;

    public Notification(HttpStatus status, String message) {
        this.status = status.getReasonPhrase();
        this.message = List.of(message);
    }

    public Notification(HttpStatus status, List<String> messages) {
        this.status = status.getReasonPhrase();
        this.message = messages;
    }
}
