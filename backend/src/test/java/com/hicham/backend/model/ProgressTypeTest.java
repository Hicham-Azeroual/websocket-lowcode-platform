package com.hicham.backend.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

class ProgressTypeTest {

    @Test
    void testProgressType_Values() {
        // Given & When
        ProgressType[] types = ProgressType.values();

        // Then
        assertNotNull(types);
        assertEquals(6, types.length, "Should have exactly 6 progress types");
    }

    @Test
    void testProgressType_GenerationStarted() {
        // Given & When
        ProgressType type = ProgressType.GENERATION_STARTED;

        // Then
        assertNotNull(type);
        assertEquals("GENERATION_STARTED", type.name());
        assertEquals(0, type.ordinal());
    }

    @Test
    void testProgressType_GenerationProgress() {
        // Given & When
        ProgressType type = ProgressType.GENERATION_PROGRESS;

        // Then
        assertNotNull(type);
        assertEquals("GENERATION_PROGRESS", type.name());
        assertEquals(1, type.ordinal());
    }

    @Test
    void testProgressType_GenerationCompleted() {
        // Given & When
        ProgressType type = ProgressType.GENERATION_COMPLETED;

        // Then
        assertNotNull(type);
        assertEquals("GENERATION_COMPLETED", type.name());
        assertEquals(2, type.ordinal());
    }

    @Test
    void testProgressType_GenerationError() {
        // Given & When
        ProgressType type = ProgressType.GENERATION_ERROR;

        // Then
        assertNotNull(type);
        assertEquals("GENERATION_ERROR", type.name());
        assertEquals(3, type.ordinal());
    }

    @Test
    void testProgressType_ValidationProgress() {
        // Given & When
        ProgressType type = ProgressType.VALIDATION_PROGRESS;

        // Then
        assertNotNull(type);
        assertEquals("VALIDATION_PROGRESS", type.name());
        assertEquals(4, type.ordinal());
    }

    @Test
    void testProgressType_DeploymentProgress() {
        // Given & When
        ProgressType type = ProgressType.DEPLOYMENT_PROGRESS;

        // Then
        assertNotNull(type);
        assertEquals("DEPLOYMENT_PROGRESS", type.name());
        assertEquals(5, type.ordinal());
    }

    @Test
    void testProgressType_ValueOf() {
        // Given
        String typeName = "GENERATION_STARTED";

        // When
        ProgressType type = ProgressType.valueOf(typeName);

        // Then
        assertNotNull(type);
        assertEquals(ProgressType.GENERATION_STARTED, type);
    }

    @Test
    void testProgressType_ToString() {
        // Given
        ProgressType type = ProgressType.GENERATION_PROGRESS;

        // When
        String result = type.toString();

        // Then
        assertNotNull(result);
        assertEquals("GENERATION_PROGRESS", result);
    }

    @Test
    void testProgressType_CompareTo() {
        // Given
        ProgressType started = ProgressType.GENERATION_STARTED;
        ProgressType progress = ProgressType.GENERATION_PROGRESS;
        ProgressType completed = ProgressType.GENERATION_COMPLETED;

        // When & Then
        assertTrue(started.compareTo(progress) < 0, "STARTED should be less than PROGRESS");
        assertTrue(progress.compareTo(completed) < 0, "PROGRESS should be less than COMPLETED");
        assertTrue(completed.compareTo(started) > 0, "COMPLETED should be greater than STARTED");
        assertEquals(0, started.compareTo(started), "Same type should be equal");
    }

    @Test
    void testProgressType_Equals() {
        // Given
        ProgressType type1 = ProgressType.GENERATION_STARTED;
        ProgressType type2 = ProgressType.GENERATION_STARTED;
        ProgressType type3 = ProgressType.GENERATION_PROGRESS;

        // When & Then
        assertTrue(type1.equals(type2), "Same enum values should be equal");
        assertTrue(type2.equals(type1), "Equality should be symmetric");
        assertTrue(!type1.equals(type3), "Different enum values should not be equal");
        assertTrue(!type1.equals(null), "Enum should not equal null");
        assertTrue(!type1.equals("string"), "Enum should not equal different type");
    }

    @Test
    void testProgressType_HashCode() {
        // Given
        ProgressType type1 = ProgressType.GENERATION_STARTED;
        ProgressType type2 = ProgressType.GENERATION_STARTED;
        ProgressType type3 = ProgressType.GENERATION_PROGRESS;

        // When & Then
        assertEquals(type1.hashCode(), type2.hashCode(), "Same enum values should have same hash code");
        assertTrue(type1.hashCode() != type3.hashCode(), "Different enum values should have different hash codes");
    }
} 