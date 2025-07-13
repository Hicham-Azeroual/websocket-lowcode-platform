package com.hicham.backend.service;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import org.mockito.Mock;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.messaging.simp.SimpMessagingTemplate;

import com.hicham.backend.model.ProgressType;
import com.hicham.backend.model.ProgressUpdate;

@ExtendWith(MockitoExtension.class)
class WebSocketProgressServiceTest {

    @Mock
    private SimpMessagingTemplate messagingTemplate;

    private WebSocketProgressService progressService;

    @BeforeEach
    void setUp() {
        progressService = new WebSocketProgressService(messagingTemplate);
    }

    @Test
    void testSendProgressUpdate_ToUser() {
        // Given
        String userId = "test-user-123";
        ProgressUpdate update = new ProgressUpdate(
            "op-123", userId, ProgressType.GENERATION_STARTED, 
            0, "init", "Starting generation", LocalDateTime.now()
        );

        // When
        progressService.sendProgressUpdate(userId, update);

        // Then
        verify(messagingTemplate, times(1)).convertAndSend(
            eq("/topic/progress." + userId),
            eq(update)
        );
    }

    @Test
    void testBroadcastSystemUpdate() {
        // Given
        ProgressUpdate update = new ProgressUpdate(
            "op-123", "system", ProgressType.GENERATION_COMPLETED, 
            100, "completed", "Generation completed", LocalDateTime.now()
        );

        // When
        progressService.broadcastSystemUpdate(update);

        // Then
        verify(messagingTemplate, times(1)).convertAndSend(
            eq("/topic/system"),
            eq(update)
        );
    }

    @Test
    void testSendProgressUpdate_WithAllProgressTypes() {
        // Given
        String userId = "test-user-123";
        ProgressType[] types = ProgressType.values();

        // When
        for (ProgressType type : types) {
            ProgressUpdate update = new ProgressUpdate(
                "op-123", userId, type, 
                50, "step", "Test message", LocalDateTime.now()
            );
            progressService.sendProgressUpdate(userId, update);
        }

        // Then
        verify(messagingTemplate, times(types.length)).convertAndSend(
            eq("/topic/progress." + userId),
            any(ProgressUpdate.class)
        );
    }

    @Test
    void testSendProgressUpdate_WhenMessagingTemplateThrowsException() {
        // Given
        String userId = "test-user-123";
        ProgressUpdate update = new ProgressUpdate(
            "op-123", userId, ProgressType.GENERATION_ERROR, 
            null, "error", "Test error", LocalDateTime.now()
        );
        
        doThrow(new RuntimeException("Messaging error"))
            .when(messagingTemplate).convertAndSend(eq("/topic/progress." + userId), any(ProgressUpdate.class));

        // When & Then
        assertThrows(RuntimeException.class, () -> {
            progressService.sendProgressUpdate(userId, update);
        });
        
        verify(messagingTemplate, times(1)).convertAndSend(
            eq("/topic/progress." + userId),
            eq(update)
        );
    }

    @Test
    void testBroadcastSystemUpdate_WhenMessagingTemplateThrowsException() {
        // Given
        ProgressUpdate update = new ProgressUpdate(
            "op-123", "system", ProgressType.GENERATION_ERROR, 
            null, "error", "System error", LocalDateTime.now()
        );
        
        doThrow(new RuntimeException("Messaging error"))
            .when(messagingTemplate).convertAndSend(eq("/topic/system"), any(ProgressUpdate.class));

        // When & Then
        assertThrows(RuntimeException.class, () -> {
            progressService.broadcastSystemUpdate(update);
        });
        
        verify(messagingTemplate, times(1)).convertAndSend(
            eq("/topic/system"),
            eq(update)
        );
    }

    @Test
    void testSendProgressUpdate_WithNullUserId() {
        // Given
        String userId = null;
        ProgressUpdate update = new ProgressUpdate(
            "op-123", userId, ProgressType.GENERATION_STARTED, 
            0, "init", "Starting generation", LocalDateTime.now()
        );

        // When
        progressService.sendProgressUpdate(userId, update);

        // Then
        verify(messagingTemplate, times(1)).convertAndSend(
            eq("/topic/progress.null"),
            eq(update)
        );
    }

    @Test
    void testSendProgressUpdate_WithEmptyUserId() {
        // Given
        String userId = "";
        ProgressUpdate update = new ProgressUpdate(
            "op-123", userId, ProgressType.GENERATION_STARTED, 
            0, "init", "Starting generation", LocalDateTime.now()
        );

        // When
        progressService.sendProgressUpdate(userId, update);

        // Then
        verify(messagingTemplate, times(1)).convertAndSend(
            eq("/topic/progress."),
            eq(update)
        );
    }

    @Test
    void testSendProgressUpdate_WithSpecialCharactersInUserId() {
        // Given
        String userId = "user@domain.com";
        ProgressUpdate update = new ProgressUpdate(
            "op-123", userId, ProgressType.GENERATION_STARTED, 
            0, "init", "Starting generation", LocalDateTime.now()
        );

        // When
        progressService.sendProgressUpdate(userId, update);

        // Then
        verify(messagingTemplate, times(1)).convertAndSend(
            eq("/topic/progress." + userId),
            eq(update)
        );
    }
} 