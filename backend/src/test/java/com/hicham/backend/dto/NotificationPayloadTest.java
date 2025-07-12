package com.hicham.backend.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

import com.hicham.backend.model.OperationStatus;

class NotificationPayloadTest {

    @Test
    void testConstructor_WithStatusAndMessage() {
        // Given
        OperationStatus status = OperationStatus.PROCESSING;
        String message = "Test message";

        // When
        NotificationPayload payload = new NotificationPayload(status, message);

        // Then
        assertNotNull(payload);
        assertEquals(status, payload.getStep());
        assertEquals(message, payload.getMessage());
        assertTrue(payload.getTimestamp() > 0);
    }

    @Test
    void testConstructor_WithNullMessage() {
        // Given
        OperationStatus status = OperationStatus.PROCESSING;
        String message = null;

        // When
        NotificationPayload payload = new NotificationPayload(status, message);

        // Then
        assertNotNull(payload);
        assertEquals(status, payload.getStep());
        assertEquals("Traitement en cours...", payload.getMessage());
        assertTrue(payload.getTimestamp() > 0);
    }

    @Test
    void testConstructor_WithEmptyMessage() {
        // Given
        OperationStatus status = OperationStatus.PROCESSING;
        String message = "";

        // When
        NotificationPayload payload = new NotificationPayload(status, message);

        // Then
        assertNotNull(payload);
        assertEquals(status, payload.getStep());
        assertEquals("Traitement en cours...", payload.getMessage());
        assertTrue(payload.getTimestamp() > 0);
    }

    @Test
    void testConstructor_WithWhitespaceMessage() {
        // Given
        OperationStatus status = OperationStatus.PROCESSING;
        String message = "   ";

        // When
        NotificationPayload payload = new NotificationPayload(status, message);

        // Then
        assertNotNull(payload);
        assertEquals(status, payload.getStep());
        assertEquals("Traitement en cours...", payload.getMessage());
        assertTrue(payload.getTimestamp() > 0);
    }

    @Test
    void testConstructor_WithAllOperationStatuses() {
        // Given
        OperationStatus[] statuses = OperationStatus.values();

        // When & Then
        for (OperationStatus status : statuses) {
            NotificationPayload payload = new NotificationPayload(status, "Test for " + status);
            
            assertNotNull(payload);
            assertEquals(status, payload.getStep());
            assertEquals("Test for " + status, payload.getMessage());
            assertTrue(payload.getTimestamp() > 0);
        }
    }

    @Test
    void testConstructor_WithVeryLongMessage() {
        // Given
        OperationStatus status = OperationStatus.PROCESSING;
        String message = "A".repeat(1000); // Very long message

        // When
        NotificationPayload payload = new NotificationPayload(status, message);

        // Then
        assertNotNull(payload);
        assertEquals(status, payload.getStep());
        assertEquals(message, payload.getMessage());
        assertTrue(payload.getTimestamp() > 0);
    }

    @Test
    void testConstructor_WithSpecialCharacters() {
        // Given
        OperationStatus status = OperationStatus.PROCESSING;
        String message = "Test message with émojis 🚀 and special chars: !@#$%^&*()";

        // When
        NotificationPayload payload = new NotificationPayload(status, message);

        // Then
        assertNotNull(payload);
        assertEquals(status, payload.getStep());
        assertEquals(message, payload.getMessage());
        assertTrue(payload.getTimestamp() > 0);
    }

    @Test
    void testTimestamp_IsCurrentTime() {
        // Given
        long beforeCreation = System.currentTimeMillis();
        
        // When
        NotificationPayload payload = new NotificationPayload(OperationStatus.PROCESSING, "Test");
        long afterCreation = System.currentTimeMillis();

        // Then
        long timestamp = payload.getTimestamp();
        assertTrue(timestamp >= beforeCreation);
        assertTrue(timestamp <= afterCreation);
    }

    @Test
    void testGetters_ReturnCorrectValues() {
        // Given
        OperationStatus status = OperationStatus.DONE;
        String message = "Operation completed";

        // When
        NotificationPayload payload = new NotificationPayload(status, message);

        // Then
        assertEquals(status, payload.getStep());
        assertEquals(message, payload.getMessage());
        assertTrue(payload.getTimestamp() > 0);
    }

    @Test
    void testMultipleInstances_HaveDifferentTimestamps() {
        // When
        NotificationPayload payload1 = new NotificationPayload(OperationStatus.STARTED, "First");
        
        // Add a small delay to ensure different timestamps
        try {
            Thread.sleep(1);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        NotificationPayload payload2 = new NotificationPayload(OperationStatus.PROCESSING, "Second");

        // Then
        assertNotEquals(payload1.getTimestamp(), payload2.getTimestamp());
    }

    @Test
    void testConstructor_WithDefaultMessages() {
        // Given
        OperationStatus[] statuses = OperationStatus.values();

        // When & Then
        for (OperationStatus status : statuses) {
            NotificationPayload payload = new NotificationPayload(status, null);
            
            assertNotNull(payload);
            assertEquals(status, payload.getStep());
            assertEquals(status.getDefaultMessage(), payload.getMessage());
            assertTrue(payload.getTimestamp() > 0);
        }
    }

    @Test
    void testConstructor_WithUnicodeCharacters() {
        // Given
        OperationStatus status = OperationStatus.PROCESSING;
        String message = "Test with unicode: αβγδε 中文 العربية";

        // When
        NotificationPayload payload = new NotificationPayload(status, message);

        // Then
        assertNotNull(payload);
        assertEquals(status, payload.getStep());
        assertEquals(message, payload.getMessage());
        assertTrue(payload.getTimestamp() > 0);
    }

    @Test
    void testConstructor_WithNewlinesAndTabs() {
        // Given
        OperationStatus status = OperationStatus.PROCESSING;
        String message = "Test with\nnewlines\tand\ttabs";

        // When
        NotificationPayload payload = new NotificationPayload(status, message);

        // Then
        assertNotNull(payload);
        assertEquals(status, payload.getStep());
        assertEquals(message, payload.getMessage());
        assertTrue(payload.getTimestamp() > 0);
    }
} 