package com.carlosramirez.livechat.controller;

import com.carlosramirez.livechat.model.dto.rest.UserDTO;
import com.carlosramirez.livechat.services.authentication.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

import static org.springframework.http.HttpStatus.UNAUTHORIZED;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody UserDTO user) {
        return authService.authenticate(user);
    }

    @PostMapping("/guest")
    public ResponseEntity<?> authenticateGuest() {
        return authService.getGuestAuthentication();
    }
}