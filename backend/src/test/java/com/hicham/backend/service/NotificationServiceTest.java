package com.hicham.backend.service;

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

import com.hicham.backend.dto.NotificationPayload;
import com.hicham.backend.model.OperationStatus;

@ExtendWith(MockitoExtension.class)
class NotificationServiceTest {

    @Mock
    private SimpMessagingTemplate messagingTemplate;

    private NotificationService notificationService;

    @BeforeEach
    void setUp() {
        notificationService = new NotificationService(messagingTemplate);
    }

    @Test
    void testSendNotification_WithStatusAndMessage() {
        // Given
        OperationStatus status = OperationStatus.PROCESSING;
        String message = "Test message";

        // When
        notificationService.sendNotification(status, message);

        // Then
        verify(messagingTemplate, times(1)).convertAndSend(
            eq("/topic/notifications"),
            any(NotificationPayload.class)
        );
    }

    @Test
    void testSendNotification_WithStatusOnly_UsesDefaultMessage() {
        // Given
        OperationStatus status = OperationStatus.PROCESSING;

        // When
        notificationService.sendNotification(status);

        // Then
        verify(messagingTemplate, times(1)).convertAndSend(
            eq("/topic/notifications"),
            any(NotificationPayload.class)
        );
    }

    @Test
    void testSendNotification_WithNullMessage_ShouldUseDefaultMessage() {
        // Given
        OperationStatus status = OperationStatus.PROCESSING;
        String message = null;

        // When
        notificationService.sendNotification(status, message);

        // Then
        verify(messagingTemplate, times(1)).convertAndSend(
            eq("/topic/notifications"),
            any(NotificationPayload.class)
        );
    }

    @Test
    void testSendNotification_WithEmptyMessage_ShouldUseDefaultMessage() {
        // Given
        OperationStatus status = OperationStatus.PROCESSING;
        String message = "";

        // When
        notificationService.sendNotification(status, message);

        // Then
        verify(messagingTemplate, times(1)).convertAndSend(
            eq("/topic/notifications"),
            any(NotificationPayload.class)
        );
    }

    @Test
    void testSendNotification_WithWhitespaceMessage_ShouldUseDefaultMessage() {
        // Given
        OperationStatus status = OperationStatus.PROCESSING;
        String message = "   ";

        // When
        notificationService.sendNotification(status, message);

        // Then
        verify(messagingTemplate, times(1)).convertAndSend(
            eq("/topic/notifications"),
            any(NotificationPayload.class)
        );
    }

    @Test
    void testSendNotification_WithAllOperationStatuses() {
        // Given
        OperationStatus[] statuses = OperationStatus.values();

        // When
        for (OperationStatus status : statuses) {
            notificationService.sendNotification(status, "Test for " + status);
        }

        // Then
        verify(messagingTemplate, times(statuses.length)).convertAndSend(
            eq("/topic/notifications"),
            any(NotificationPayload.class)
        );
    }

    @Test
    void testSendNotification_WithVeryLongMessage() {
        // Given
        OperationStatus status = OperationStatus.PROCESSING;
        String message = "A".repeat(1000); // Very long message

        // When
        notificationService.sendNotification(status, message);

        // Then
        verify(messagingTemplate, times(1)).convertAndSend(
            eq("/topic/notifications"),
            any(NotificationPayload.class)
        );
    }

    @Test
    void testSendNotification_WithSpecialCharacters() {
        // Given
        OperationStatus status = OperationStatus.PROCESSING;
        String message = "Test message with émojis 🚀 and special chars: !@#$%^&*()";

        // When
        notificationService.sendNotification(status, message);

        // Then
        verify(messagingTemplate, times(1)).convertAndSend(
            eq("/topic/notifications"),
            any(NotificationPayload.class)
        );
    }

    @Test
    void testSendNotification_WhenMessagingTemplateThrowsException() {
        // Given
        OperationStatus status = OperationStatus.PROCESSING;
        String message = "Test message";
        
        doThrow(new RuntimeException("Messaging error"))
            .when(messagingTemplate).convertAndSend(eq("/topic/notifications"), any(NotificationPayload.class));

        // When & Then
        assertThrows(RuntimeException.class, () -> {
            notificationService.sendNotification(status, message);
        });
        
        verify(messagingTemplate, times(1)).convertAndSend(
            eq("/topic/notifications"),
            any(NotificationPayload.class)
        );
    }

    @Test
    void testSendNotification_VerifyPayloadContent() {
        // Given
        OperationStatus status = OperationStatus.DONE;
        String message = "Operation completed successfully";

        // When
        notificationService.sendNotification(status, message);

        // Then
        verify(messagingTemplate, times(1)).convertAndSend(
            eq("/topic/notifications"),
            any(NotificationPayload.class)
        );
    }
} 