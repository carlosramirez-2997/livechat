package com.carlosramirez.livechat.model.dto.rest;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.util.List;

@Data
@Builder(toBuilder = true)
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse<T> {
    private T body;
    private Notification notification;

    public static <T> ApiResponse<T> success(T body) {
        return ApiResponse.<T>builder().body(body)
                .build();
    }

    public static <T> ApiResponse<T> error(HttpStatus status, String message) {
        return ApiResponse.<T>builder().body(null)
                .notification(new Notification(status, message))
                .build();
    }

    public static <T> ApiResponse<T> error(HttpStatus status, List<String> messages) {
        return ApiResponse.<T>builder().body(null)
                .notification(new Notification(status, messages))
                .build();
    }
}
