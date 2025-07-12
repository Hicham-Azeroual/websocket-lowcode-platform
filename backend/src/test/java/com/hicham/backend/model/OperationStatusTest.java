package com.hicham.backend.model;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

class OperationStatusTest {

    @Test
    void testEnumValues() {
        // When
        OperationStatus[] values = OperationStatus.values();

        // Then
        assertEquals(3, values.length);
        assertArrayEquals(new OperationStatus[]{
            OperationStatus.STARTED,
            OperationStatus.PROCESSING,
            OperationStatus.DONE
        }, values);
    }

    @Test
    void testValueOf() {
        // When & Then
        assertEquals(OperationStatus.STARTED, OperationStatus.valueOf("STARTED"));
        assertEquals(OperationStatus.PROCESSING, OperationStatus.valueOf("PROCESSING"));
        assertEquals(OperationStatus.DONE, OperationStatus.valueOf("DONE"));
    }

    @Test
    void testValueOf_WithInvalidValue() {
        // When & Then
        assertThrows(IllegalArgumentException.class, () -> {
            OperationStatus.valueOf("INVALID");
        });
    }

    @Test
    void testGetDefaultMessage_ForStarted() {
        // When
        String message = OperationStatus.STARTED.getDefaultMessage();

        // Then
        assertEquals("Opération démarrée", message);
    }

    @Test
    void testGetDefaultMessage_ForProcessing() {
        // When
        String message = OperationStatus.PROCESSING.getDefaultMessage();

        // Then
        assertEquals("Traitement en cours...", message);
    }

    @Test
    void testGetDefaultMessage_ForDone() {
        // When
        String message = OperationStatus.DONE.getDefaultMessage();

        // Then
        assertEquals("Opération terminée", message);
    }

    @Test
    void testGetDefaultMessage_ForAllStatuses() {
        // When & Then
        for (OperationStatus status : OperationStatus.values()) {
            String message = status.getDefaultMessage();
            assertNotNull(message);
            assertFalse(message.isEmpty());
        }
    }

    @Test
    void testEnumEquality() {
        // When & Then
        assertSame(OperationStatus.STARTED, OperationStatus.STARTED);
        assertSame(OperationStatus.PROCESSING, OperationStatus.PROCESSING);
        assertSame(OperationStatus.DONE, OperationStatus.DONE);
        
        assertNotSame(OperationStatus.STARTED, OperationStatus.PROCESSING);
        assertNotSame(OperationStatus.PROCESSING, OperationStatus.DONE);
        assertNotSame(OperationStatus.STARTED, OperationStatus.DONE);
    }

    @Test
    void testEnumOrdinal() {
        // When & Then
        assertEquals(0, OperationStatus.STARTED.ordinal());
        assertEquals(1, OperationStatus.PROCESSING.ordinal());
        assertEquals(2, OperationStatus.DONE.ordinal());
    }

    @Test
    void testEnumName() {
        // When & Then
        assertEquals("STARTED", OperationStatus.STARTED.name());
        assertEquals("PROCESSING", OperationStatus.PROCESSING.name());
        assertEquals("DONE", OperationStatus.DONE.name());
    }

    @Test
    void testEnumToString() {
        // When & Then
        assertEquals("STARTED", OperationStatus.STARTED.toString());
        assertEquals("PROCESSING", OperationStatus.PROCESSING.toString());
        assertEquals("DONE", OperationStatus.DONE.toString());
    }

    @Test
    void testDefaultMessagesAreUnique() {
        // When
        String startedMessage = OperationStatus.STARTED.getDefaultMessage();
        String processingMessage = OperationStatus.PROCESSING.getDefaultMessage();
        String doneMessage = OperationStatus.DONE.getDefaultMessage();

        // Then
        assertNotEquals(startedMessage, processingMessage);
        assertNotEquals(processingMessage, doneMessage);
        assertNotEquals(startedMessage, doneMessage);
    }

    @Test
    void testDefaultMessagesAreNotNull() {
        // When & Then
        for (OperationStatus status : OperationStatus.values()) {
            assertNotNull(status.getDefaultMessage());
        }
    }

    @Test
    void testDefaultMessagesAreNotEmpty() {
        // When & Then
        for (OperationStatus status : OperationStatus.values()) {
            assertFalse(status.getDefaultMessage().isEmpty());
        }
    }

    @Test
    void testDefaultMessagesAreNotBlank() {
        // When & Then
        for (OperationStatus status : OperationStatus.values()) {
            assertFalse(status.getDefaultMessage().trim().isEmpty());
        }
    }

    @Test
    void testEnumComparison() {
        // When & Then
        assertTrue(OperationStatus.STARTED.compareTo(OperationStatus.PROCESSING) < 0);
        assertTrue(OperationStatus.PROCESSING.compareTo(OperationStatus.DONE) < 0);
        assertTrue(OperationStatus.STARTED.compareTo(OperationStatus.DONE) < 0);
        
        assertTrue(OperationStatus.PROCESSING.compareTo(OperationStatus.STARTED) > 0);
        assertTrue(OperationStatus.DONE.compareTo(OperationStatus.PROCESSING) > 0);
        assertTrue(OperationStatus.DONE.compareTo(OperationStatus.STARTED) > 0);
        
        assertEquals(0, OperationStatus.STARTED.compareTo(OperationStatus.STARTED));
        assertEquals(0, OperationStatus.PROCESSING.compareTo(OperationStatus.PROCESSING));
        assertEquals(0, OperationStatus.DONE.compareTo(OperationStatus.DONE));
    }
} 