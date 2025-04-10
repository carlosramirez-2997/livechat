package com.carlosramirez.livechat.controller;

import com.carlosramirez.livechat.model.dto.rest.AuthUserDTO;
import com.carlosramirez.livechat.services.authentication.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody AuthUserDTO user) {
        return authService.authenticate(user);
    }

    @PostMapping("/guest")
    public ResponseEntity<?> authenticateGuest() {
        return authService.getGuestAuthentication();
    }

}