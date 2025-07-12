package com.hicham.backend.model;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;

class ProgressTypeTest {

    @Test
    void testEnumValues() {
        // When
        ProgressType[] values = ProgressType.values();

        // Then
        assertEquals(6, values.length);
        assertArrayEquals(new ProgressType[]{
            ProgressType.GENERATION_STARTED,
            ProgressType.GENERATION_PROGRESS,
            ProgressType.GENERATION_COMPLETED,
            ProgressType.GENERATION_ERROR,
            ProgressType.VALIDATION_PROGRESS,
            ProgressType.DEPLOYMENT_PROGRESS
        }, values);
    }

    @Test
    void testValueOf() {
        // When & Then
        assertEquals(ProgressType.GENERATION_STARTED, ProgressType.valueOf("GENERATION_STARTED"));
        assertEquals(ProgressType.GENERATION_PROGRESS, ProgressType.valueOf("GENERATION_PROGRESS"));
        assertEquals(ProgressType.GENERATION_COMPLETED, ProgressType.valueOf("GENERATION_COMPLETED"));
        assertEquals(ProgressType.GENERATION_ERROR, ProgressType.valueOf("GENERATION_ERROR"));
        assertEquals(ProgressType.VALIDATION_PROGRESS, ProgressType.valueOf("VALIDATION_PROGRESS"));
        assertEquals(ProgressType.DEPLOYMENT_PROGRESS, ProgressType.valueOf("DEPLOYMENT_PROGRESS"));
    }

    @Test
    void testValueOf_WithInvalidValue() {
        // When & Then
        assertThrows(IllegalArgumentException.class, () -> {
            ProgressType.valueOf("INVALID");
        });
    }

    @Test
    void testEnumOrdinal() {
        // When & Then
        assertEquals(0, ProgressType.GENERATION_STARTED.ordinal());
        assertEquals(1, ProgressType.GENERATION_PROGRESS.ordinal());
        assertEquals(2, ProgressType.GENERATION_COMPLETED.ordinal());
        assertEquals(3, ProgressType.GENERATION_ERROR.ordinal());
        assertEquals(4, ProgressType.VALIDATION_PROGRESS.ordinal());
        assertEquals(5, ProgressType.DEPLOYMENT_PROGRESS.ordinal());
    }

    @Test
    void testEnumName() {
        // When & Then
        assertEquals("GENERATION_STARTED", ProgressType.GENERATION_STARTED.name());
        assertEquals("GENERATION_PROGRESS", ProgressType.GENERATION_PROGRESS.name());
        assertEquals("GENERATION_COMPLETED", ProgressType.GENERATION_COMPLETED.name());
        assertEquals("GENERATION_ERROR", ProgressType.GENERATION_ERROR.name());
        assertEquals("VALIDATION_PROGRESS", ProgressType.VALIDATION_PROGRESS.name());
        assertEquals("DEPLOYMENT_PROGRESS", ProgressType.DEPLOYMENT_PROGRESS.name());
    }

    @Test
    void testEnumToString() {
        // When & Then
        assertEquals("GENERATION_STARTED", ProgressType.GENERATION_STARTED.toString());
        assertEquals("GENERATION_PROGRESS", ProgressType.GENERATION_PROGRESS.toString());
        assertEquals("GENERATION_COMPLETED", ProgressType.GENERATION_COMPLETED.toString());
        assertEquals("GENERATION_ERROR", ProgressType.GENERATION_ERROR.toString());
        assertEquals("VALIDATION_PROGRESS", ProgressType.VALIDATION_PROGRESS.toString());
        assertEquals("DEPLOYMENT_PROGRESS", ProgressType.DEPLOYMENT_PROGRESS.toString());
    }
} 