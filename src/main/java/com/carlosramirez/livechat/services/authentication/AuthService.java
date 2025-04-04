package com.carlosramirez.livechat.services.authentication;

import com.carlosramirez.livechat.model.dto.rest.UserDTO;
import org.springframework.http.ResponseEntity;

public interface AuthService {
    ResponseEntity<?> authenticate(UserDTO user);

    ResponseEntity<?> getGuestAuthentication();
}
