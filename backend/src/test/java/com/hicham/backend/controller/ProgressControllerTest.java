package com.hicham.backend.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.messaging.simp.SimpMessagingTemplate;

import com.hicham.backend.service.WebSocketProgressService;

import java.security.Principal;

@ExtendWith(MockitoExtension.class)
class ProgressControllerTest {

    @Mock
    private WebSocketProgressService progressService;

    @Mock
    private Principal user;

    private ProgressController controller;

    @BeforeEach
    void setUp() {
//        controller = new ProgressController();
//        // Use reflection to set the private field since it's autowired
//        try {
//            java.lang.reflect.Field field = ProgressController.class.getDeclaredField("progressService");
//            field.setAccessible(true);
//            field.set(controller, progressService);
//        } catch (Exception e) {
//            throw new RuntimeException("Failed to set up test", e);
//        }
    }

    @Test
    void testSubscribeToProgress_Success() {
//        // Given
//        String expectedResponse = "Subscribed to progress updates";
//
//        // When
//        String response = controller.subscribeToProgress(user);
//
//        // Then
//        assertNotNull(response);
//        assertEquals(expectedResponse, response);
    }

    @Test
    void testSubscribeToProgress_WithNullUser() {
        // Given
        String expectedResponse = "Subscribed to progress updates";

        // When
        String response = controller.subscribeToProgress(null);

        // Then
        assertNotNull(response);
        assertEquals(expectedResponse, response);
    }

    @Test
    void testSubscribeToProgress_ReturnsCorrectMessage() {
//        // Given
//        String expectedResponse = "Subscribed to progress updates";
//
//        // When
//        String response = controller.subscribeToProgress(user);
//
//        // Then
//        assertEquals(expectedResponse, response);
    }
} 