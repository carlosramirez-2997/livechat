package com.carlosramirez.livechat.controllers;

import com.carlosramirez.livechat.LiveChatApplicationTest;
import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@EnableMethodSecurity
public class UserControllerTest extends LiveChatApplicationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private Flyway flyway;

    @BeforeEach
    void setUp() {
        flyway.clean();
        flyway.migrate();
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void getUsers_asAdminRole_thenReturnOk() throws Exception {
        String jsonRequest = readJsonFile("src/test/resources/data/controller/output/valid-user-response-default-pagination-data.json");

        mockMvc.perform(get("/api/users"))
                .andExpect(status().isOk())
                .andExpect(content().json(jsonRequest));
    }

    @Test
    @WithMockUser(username = "commonuser", roles = {"USER"})
    void getUsers_asUserRole_thenReturnForbidden() throws Exception {
        String jsonRequest = readJsonFile("src/test/resources/data/controller/output/invalid-request-by-permissions.json");

        mockMvc.perform(get("/api/users"))
                .andExpect(status().isForbidden())
                .andExpect(content().json(jsonRequest));;
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void getUsers_asAdmin_whenRequestDataWithInvalidPaginationData_thenReturnError() throws Exception {
        String jsonRequest = readJsonFile("src/test/resources/data/controller/output/invalid-request-by-pagination.json");

        mockMvc.perform(get("/api/users?page=0&size=101"))
                .andExpect(status().isBadRequest())
                .andExpect(content().json(jsonRequest));
    }
}
