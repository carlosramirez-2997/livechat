package com.carlosramirez.livechat.controllers;

import com.carlosramirez.livechat.LiveChatApplicationTest;
import com.carlosramirez.livechat.controller.AuthController;
import com.carlosramirez.livechat.controller.ExceptionHandlerController;
import com.carlosramirez.livechat.services.authentication.AuthService;
import com.carlosramirez.livechat.utils.JwtUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
public class AuthControllerTest extends LiveChatApplicationTest {

    private MockMvc mockMvc;

    @InjectMocks
    private ExceptionHandlerController exceptionHandlerController;

    @InjectMocks
    private AuthController authController;

    @Mock
    private AuthService authService;

    @Mock
    private JwtUtils jwtUtils;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(authController)
                .setControllerAdvice(exceptionHandlerController)
                .build();
    }

    @Test
    void testAuthenticateInvalidEmail() throws Exception {
        String jsonRequest = readJsonFile("src/test/resources/data/controller/input/invalid_email.json");

        mockMvc.perform(MockMvcRequestBuilders.post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.notification.message[0]").value("email: Invalid email format"));
    }

    @Test
    void testAuthenticateBadCredentials() throws Exception {
        String jsonRequest = readJsonFile("src/test/resources/data/controller/input/invalid_credentials.json");

        Mockito.when(authService.authenticate(any()))
                .thenThrow(new BadCredentialsException("Invalid username or password"));

        mockMvc.perform(MockMvcRequestBuilders.post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isUnauthorized());
    }
}
