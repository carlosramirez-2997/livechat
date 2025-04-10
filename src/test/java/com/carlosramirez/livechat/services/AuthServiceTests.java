package com.carlosramirez.livechat.services;

import com.carlosramirez.livechat.data.UserRepository;
import com.carlosramirez.livechat.model.dto.rest.AuthUserDTO;
import com.carlosramirez.livechat.services.authentication.AuthService;
import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@ActiveProfiles("test")
public class AuthServiceTests {

    @Autowired
    private Flyway flyway;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthService authService;

    @BeforeEach
    void setUp() {
        flyway.clean();
        flyway.migrate();
    }

    @Test
    public void testLogin_whenUserCredentialsAreWrong_shouldReturn400() {
        AuthUserDTO authUserDTO = getInvalidMockData();

        assertThrows(BadCredentialsException.class, () -> {
            authService.authenticate(authUserDTO);
        });
    }

    @Test
    public void testLogin_whenUserCredentialsAreCorrect_thenReturn200() {
        AuthUserDTO authUserDTO = getValidMockData();

        ResponseEntity<?> response = authService.authenticate(authUserDTO);

        Assertions.assertSame(HttpStatus.OK, response.getStatusCode());
    }

    private AuthUserDTO getValidMockData() {
        return AuthUserDTO.builder()
                .email("emma.jones@example.com")
                .password("Password123!")
                .build();
    }

    private AuthUserDTO getInvalidMockData() {
        return AuthUserDTO.builder()
                .email("quentin.tiegs@example.com")
                .password("InvalidPassword!")
                .build();
    }
}
