package com.carlosramirez.livechat.services.authentication.impl;

import com.carlosramirez.livechat.model.dto.rest.UserDTO;
import com.carlosramirez.livechat.model.entity.BaseUser;
import com.carlosramirez.livechat.model.entity.CustomUserDetails;
import com.carlosramirez.livechat.model.entity.GuestUser;
import com.carlosramirez.livechat.services.authentication.AuthService;
import com.carlosramirez.livechat.utilities.JwtUtil;
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
    private final JwtUtil jwtUtil;

    public JwtAuthenticationService(AuthenticationManager authenticationManager, JwtUtil jwtUtil) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
    }

    public ResponseEntity<Map<String, Object>> authenticate(UserDTO user) {
        Authentication auth = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword()));

        CustomUserDetails userDetails = (CustomUserDetails) auth.getPrincipal();

        String email = userDetails.getEmail();
        String username = userDetails.getUsername();
        List<GrantedAuthority> roles = (List<GrantedAuthority>) userDetails.getAuthorities();

        String token = jwtUtil.generateToken(email, username, roles);

        return ResponseEntity.status(OK).body(Map.of("token", token));
    }

    @Override
    public ResponseEntity<?> getGuestAuthentication() {
        String guestUsername = "Guest_" + UUID.randomUUID().toString().substring(0, 5);

        BaseUser guest = new GuestUser();
        guest.setUsername(guestUsername);
        guest.setRole("GUEST");

        String token = jwtUtil.generateToken(null, guest.getUsername(), List.of(new SimpleGrantedAuthority("GUEST")));

        return ResponseEntity.status(OK).body(Map.of("token", token));
    }
}
