package com.carlosramirez.livechat.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class JwtUtils {

    @Autowired
    ObjectMapper objectMapper;

    private final Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    private final long EXPIRATION_TIME = 1000 * 60 * 60 * 24; // 24 hours

    @SneakyThrows
    public String generateToken(String email, String username, List<GrantedAuthority> roles) {
        String rolesJson = objectMapper.writeValueAsString(roles.stream().map(GrantedAuthority::getAuthority).toList());

        return Jwts.builder()
                .setSubject(email)
                .claim("username", username)
                .claim("roles", rolesJson)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(key)
                .compact();
    }

    public String extractUsername(String token) {
        return extractClaim(token, (c) -> c.get("username")).toString();
    }

    public boolean validateToken(String token) {
        try {
            Claims claims = extractAllClaims(token);

            Date expiration = claims.getExpiration();
            return expiration != null && expiration.after(new Date());
        } catch (Exception e) {
            return false;
        }
    }

    public List<SimpleGrantedAuthority> extractRoleFromToken(String token) {
        String rolesJson = extractClaim(token, claims -> claims.get("roles", String.class));

        try {
            List<String> roles = objectMapper.readValue(rolesJson, List.class);
            return roles.stream()
                    .map(SimpleGrantedAuthority::new)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new RuntimeException("Failed to extract roles from token", e);
        }
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {

        final Claims claims = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token).getBody();
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}