package com.hicham.backend.model;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

class ProgressUpdateTest {

    @Test
    void testProgressUpdate_Constructor() {
        // Given
        String operationId = "test-op-123";
        String userId = "test-user-123";
        ProgressType type = ProgressType.GENERATION_STARTED;
        Integer percentage = 0;
        String step = "init";
        String message = "Test message";
        LocalDateTime timestamp = LocalDateTime.now();

        // When
        ProgressUpdate update = new ProgressUpdate(operationId, userId, type, percentage, step, message, timestamp);

        // Then
        assertNotNull(update);
        assertEquals(operationId, update.getOperationId());
        assertEquals(userId, update.getUserId());
        assertEquals(type, update.getType());
        assertEquals(percentage, update.getPercentage());
        assertEquals(step, update.getStep());
        assertEquals(message, update.getMessage());
        assertEquals(timestamp, update.getTimestamp());
    }

    @Test
    void testProgressUpdate_WithNullValues() {
        // Given
        String operationId = null;
        String userId = null;
        ProgressType type = null;
        Integer percentage = null;
        String step = null;
        String message = null;
        LocalDateTime timestamp = null;

        // When
        ProgressUpdate update = new ProgressUpdate(operationId, userId, type, percentage, step, message, timestamp);

        // Then
        assertNotNull(update);
        assertNull(update.getOperationId());
        assertNull(update.getUserId());
        assertNull(update.getType());
        assertNull(update.getPercentage());
        assertNull(update.getStep());
        assertNull(update.getMessage());
        assertNull(update.getTimestamp());
    }

    @Test
    void testProgressUpdate_WithEmptyStrings() {
        // Given
        String operationId = "";
        String userId = "";
        ProgressType type = ProgressType.GENERATION_STARTED;
        Integer percentage = 0;
        String step = "";
        String message = "";
        LocalDateTime timestamp = LocalDateTime.now();

        // When
        ProgressUpdate update = new ProgressUpdate(operationId, userId, type, percentage, step, message, timestamp);

        // Then
        assertNotNull(update);
        assertEquals("", update.getOperationId());
        assertEquals("", update.getUserId());
        assertEquals(type, update.getType());
        assertEquals(percentage, update.getPercentage());
        assertEquals("", update.getStep());
        assertEquals("", update.getMessage());
        assertEquals(timestamp, update.getTimestamp());
    }

    @Test
    void testProgressUpdate_WithSpecialCharacters() {
        // Given
        String operationId = "op-émojis-🚀";
        String userId = "user@domain.com";
        ProgressType type = ProgressType.GENERATION_PROGRESS;
        Integer percentage = 50;
        String step = "step-émojis-🚀";
        String message = "Message with émojis 🚀";
        LocalDateTime timestamp = LocalDateTime.now();

        // When
        ProgressUpdate update = new ProgressUpdate(operationId, userId, type, percentage, step, message, timestamp);

        // Then
        assertNotNull(update);
        assertEquals(operationId, update.getOperationId());
        assertEquals(userId, update.getUserId());
        assertEquals(type, update.getType());
        assertEquals(percentage, update.getPercentage());
        assertEquals(step, update.getStep());
        assertEquals(message, update.getMessage());
        assertEquals(timestamp, update.getTimestamp());
    }

    @Test
    void testProgressUpdate_WithAllProgressTypes() {
        // Given
        String operationId = "test-op-123";
        String userId = "test-user-123";
        Integer percentage = 100;
        String step = "test";
        String message = "Test message";
        LocalDateTime timestamp = LocalDateTime.now();

        // When & Then - Test all progress types
        for (ProgressType type : ProgressType.values()) {
            ProgressUpdate update = new ProgressUpdate(operationId, userId, type, percentage, step, message, timestamp);
            assertNotNull(update);
            assertEquals(type, update.getType());
        }
    }

    @Test
    void testProgressUpdate_WithDifferentPercentages() {
        // Given
        String operationId = "test-op-123";
        String userId = "test-user-123";
        ProgressType type = ProgressType.GENERATION_PROGRESS;
        String step = "test";
        String message = "Test message";
        LocalDateTime timestamp = LocalDateTime.now();

        // Test different percentage values
        Integer[] percentages = {0, 25, 50, 75, 100, null};

        for (Integer percentage : percentages) {
            // When
            ProgressUpdate update = new ProgressUpdate(operationId, userId, type, percentage, step, message, timestamp);

            // Then
            assertNotNull(update);
            assertEquals(percentage, update.getPercentage());
        }
    }

    @Test
    void testProgressUpdate_ToString() {
        // Given
        String operationId = "test-op-123";
        String userId = "test-user-123";
        ProgressType type = ProgressType.GENERATION_STARTED;
        Integer percentage = 0;
        String step = "init";
        String message = "Test message";
        LocalDateTime timestamp = LocalDateTime.now();

        ProgressUpdate update = new ProgressUpdate(operationId, userId, type, percentage, step, message, timestamp);

        // When
        String result = update.toString();

        // Then
        assertNotNull(result);
        assertTrue(result.contains(operationId));
        assertTrue(result.contains(userId));
        assertTrue(result.contains(type.toString()));
        assertTrue(result.contains(step));
        assertTrue(result.contains(message));
    }
} 