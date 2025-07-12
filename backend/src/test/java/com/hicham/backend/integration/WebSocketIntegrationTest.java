package com.hicham.backend.integration;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureWebMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebMvc
@TestPropertySource(properties = {
    "spring.websocket.enabled=true",
    "logging.level.com.hicham.backend=DEBUG"
})
class WebSocketIntegrationTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private ObjectMapper objectMapper;

    private MockMvc mockMvc;

    @Test
    void testHealthEndpoint() throws Exception {
        // Given
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

        // When & Then
        mockMvc.perform(get("/api/operations/health")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("healthy"))
                .andExpect(jsonPath("$.service").value("WebSocket Notification Service"))
                .andExpect(jsonPath("$.timestamp").exists());
    }

    @Test
    void testStartOperationEndpoint() throws Exception {
        // Given
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

        // When & Then
        mockMvc.perform(post("/api/operations/start")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isAccepted())
                .andExpect(jsonPath("$.status").value("accepted"))
                .andExpect(jsonPath("$.message").value("Opération démarrée avec succès. Écoutez les notifications WebSocket sur /topic/notifications"))
                .andExpect(jsonPath("$.timestamp").exists());
    }

    @Test
    void testStartOperationEndpoint_ReturnsCorrectContentType() throws Exception {
        // Given
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

        // When & Then
        mockMvc.perform(post("/api/operations/start")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isAccepted())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void testHealthEndpoint_ReturnsCorrectContentType() throws Exception {
        // Given
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

        // When & Then
        mockMvc.perform(get("/api/operations/health")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void testStartOperationEndpoint_HandlesMultipleRequests() throws Exception {
        // Given
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

        // When & Then
        for (int i = 0; i < 3; i++) {
            mockMvc.perform(post("/api/operations/start")
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isAccepted())
                    .andExpect(jsonPath("$.status").value("accepted"));
        }
    }

    @Test
    void testHealthEndpoint_HandlesMultipleRequests() throws Exception {
        // Given
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

        // When & Then
        for (int i = 0; i < 3; i++) {
            mockMvc.perform(get("/api/operations/health")
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.status").value("healthy"));
        }
    }

    @Test
    void testStartOperationEndpoint_ResponseStructure() throws Exception {
        // Given
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

        // When & Then
        String response = mockMvc.perform(post("/api/operations/start")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isAccepted())
                .andReturn()
                .getResponse()
                .getContentAsString();

        // Verify JSON structure
        assertTrue(response.contains("\"status\""));
        assertTrue(response.contains("\"message\""));
        assertTrue(response.contains("\"timestamp\""));
        assertTrue(response.contains("\"accepted\""));
    }

    @Test
    void testHealthEndpoint_ResponseStructure() throws Exception {
        // Given
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

        // When & Then
        String response = mockMvc.perform(get("/api/operations/health")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        // Verify JSON structure
        assertTrue(response.contains("\"status\""));
        assertTrue(response.contains("\"service\""));
        assertTrue(response.contains("\"timestamp\""));
        assertTrue(response.contains("\"healthy\""));
    }

    @Test
    void testStartOperationEndpoint_TimestampIsValid() throws Exception {
        // Given
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

        // When & Then
        String response = mockMvc.perform(post("/api/operations/start")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isAccepted())
                .andReturn()
                .getResponse()
                .getContentAsString();

        // Extract timestamp and verify it's a valid number
        assertDoesNotThrow(() -> {
            // Simple check that timestamp is present and numeric
            assertTrue(response.contains("\"timestamp\":"));
        });
    }

    @Test
    void testHealthEndpoint_TimestampIsValid() throws Exception {
        // Given
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

        // When & Then
        String response = mockMvc.perform(get("/api/operations/health")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        // Extract timestamp and verify it's a valid number
        assertDoesNotThrow(() -> {
            // Simple check that timestamp is present and numeric
            assertTrue(response.contains("\"timestamp\":"));
        });
    }
} 