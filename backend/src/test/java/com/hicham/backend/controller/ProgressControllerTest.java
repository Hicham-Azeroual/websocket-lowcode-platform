package com.hicham.backend.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ProgressControllerTest {

    private ProgressController controller;

    @BeforeEach
    void setUp() {
        controller = new ProgressController();
    }

    @Test
    void testSubscribeToProgress_Success() {
        // Given
        String userId = "test-user-123";
        String expectedResponse = "Subscribed to progress updates for user: " + userId;

        // When
        String response = controller.subscribeToProgress(userId);

        // Then
        assertNotNull(response);
        assertEquals(expectedResponse, response);
    }

    @Test
    void testSubscribeToProgress_WithNullUserId() {
        // Given
        String userId = null;
        String expectedResponse = "Subscribed to progress updates for user: null";

        // When
        String response = controller.subscribeToProgress(userId);

        // Then
        assertNotNull(response);
        assertEquals(expectedResponse, response);
    }

    @Test
    void testSubscribeToProgress_WithEmptyUserId() {
        // Given
        String userId = "";
        String expectedResponse = "Subscribed to progress updates for user: ";

        // When
        String response = controller.subscribeToProgress(userId);

        // Then
        assertNotNull(response);
        assertEquals(expectedResponse, response);
    }

    @Test
    void testSubscribeToProgress_WithSpecialCharacters() {
        // Given
        String userId = "user@domain.com";
        String expectedResponse = "Subscribed to progress updates for user: " + userId;

        // When
        String response = controller.subscribeToProgress(userId);

        // Then
        assertNotNull(response);
        assertEquals(expectedResponse, response);
    }

    @Test
    void testSubscribeToProgress_WithUnicodeCharacters() {
        // Given
        String userId = "user-émojis-🚀";
        String expectedResponse = "Subscribed to progress updates for user: " + userId;

        // When
        String response = controller.subscribeToProgress(userId);

        // Then
        assertNotNull(response);
        assertEquals(expectedResponse, response);
    }
} 