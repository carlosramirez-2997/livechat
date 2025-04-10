package com.carlosramirez.livechat.services.authentication.impl;

import com.carlosramirez.livechat.model.dto.rest.AuthUserDTO;
import com.carlosramirez.livechat.model.entity.CustomUserDetails;
import com.carlosramirez.livechat.services.authentication.AuthService;
import com.carlosramirez.livechat.utils.JwtUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import static org.springframework.http.HttpStatus.OK;

@Service
public class JwtAuthenticationService implements AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;

    public JwtAuthenticationService(AuthenticationManager authenticationManager, JwtUtils jwtUtils) {
        this.authenticationManager = authenticationManager;
        this.jwtUtils = jwtUtils;
    }

    public ResponseEntity<Map<String, Object>> authenticate(AuthUserDTO user) {
        Authentication auth = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword()));

        CustomUserDetails userDetails = (CustomUserDetails) auth.getPrincipal();

        String email = userDetails.getEmail();
        String username = userDetails.getUsername();
        List<GrantedAuthority> roles = (List<GrantedAuthority>) userDetails.getAuthorities();

        String token = jwtUtils.generateToken(email, username, roles);

        return ResponseEntity.status(OK).body(Map.of("token", token));
    }

    @Override
    public ResponseEntity<?> getGuestAuthentication() {
        String guestUsername = "Guest_" + UUID.randomUUID().toString().substring(0, 5);

        String token = jwtUtils.generateToken(null, guestUsername, List.of(new SimpleGrantedAuthority("ROLE_GUEST")));

        return ResponseEntity.status(OK).body(Map.of("token", token));
    }
}