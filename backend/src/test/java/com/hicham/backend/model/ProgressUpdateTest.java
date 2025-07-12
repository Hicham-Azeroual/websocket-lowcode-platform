package com.hicham.backend.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

class ProgressUpdateTest {

    @Test
    void testConstructor_WithAllParameters() {
        // Given
        String operationId = "op-123";
        String userId = "user-456";
        ProgressType type = ProgressType.GENERATION_STARTED;
        Integer percentage = 0;
        String step = "init";
        String message = "Starting generation";
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
    void testConstructor_WithNullPercentage() {
        // Given
        String operationId = "op-123";
        String userId = "user-456";
        ProgressType type = ProgressType.GENERATION_ERROR;
        Integer percentage = null;
        String step = "error";
        String message = "Error occurred";
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
    void testConstructor_WithAllProgressTypes() {
        // Given
        String operationId = "op-123";
        String userId = "user-456";
        String step = "test";
        String message = "Test message";
        LocalDateTime timestamp = LocalDateTime.now();

        // When & Then
        for (ProgressType type : ProgressType.values()) {
            ProgressUpdate update = new ProgressUpdate(operationId, userId, type, 50, step, message, timestamp);
            
            assertNotNull(update);
            assertEquals(operationId, update.getOperationId());
            assertEquals(userId, update.getUserId());
            assertEquals(type, update.getType());
            assertEquals(50, update.getPercentage());
            assertEquals(step, update.getStep());
            assertEquals(message, update.getMessage());
            assertEquals(timestamp, update.getTimestamp());
        }
    }

    @Test
    void testConstructor_WithEmptyStrings() {
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
        assertEquals(operationId, update.getOperationId());
        assertEquals(userId, update.getUserId());
        assertEquals(type, update.getType());
        assertEquals(percentage, update.getPercentage());
        assertEquals(step, update.getStep());
        assertEquals(message, update.getMessage());
        assertEquals(timestamp, update.getTimestamp());
    }

    @Test
    void testConstructor_WithSpecialCharacters() {
        // Given
        String operationId = "op-123@domain.com";
        String userId = "user-456@test.org";
        ProgressType type = ProgressType.GENERATION_PROGRESS;
        Integer percentage = 75;
        String step = "step_with_special_chars";
        String message = "Message with émojis 🚀 and special chars: !@#$%^&*()";
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
    void testConstructor_WithVeryLongStrings() {
        // Given
        String operationId = "A".repeat(1000);
        String userId = "B".repeat(1000);
        ProgressType type = ProgressType.GENERATION_COMPLETED;
        Integer percentage = 100;
        String step = "C".repeat(1000);
        String message = "D".repeat(1000);
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
} 