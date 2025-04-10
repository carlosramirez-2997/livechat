package com.carlosramirez.livechat.services.authentication;

import com.carlosramirez.livechat.model.dto.rest.AuthUserDTO;
import org.springframework.http.ResponseEntity;

public interface AuthService {
    ResponseEntity<?> authenticate(AuthUserDTO user);

    ResponseEntity<?> getGuestAuthentication();

}
