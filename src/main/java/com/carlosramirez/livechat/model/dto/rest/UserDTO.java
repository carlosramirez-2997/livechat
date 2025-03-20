package com.carlosramirez.livechat.model.dto.rest;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserDTO {
    @JsonProperty("email")
    private String email;

    @JsonProperty("password")
    private String password;
}
