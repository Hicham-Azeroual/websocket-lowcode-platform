package com.hicham.backend.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.eq;
import org.mockito.Mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import org.mockito.junit.jupiter.MockitoExtension;

import com.hicham.backend.service.ArchitectureGenerationService;

@ExtendWith(MockitoExtension.class)
class GenerationControllerTest {

    @Mock
    private ArchitectureGenerationService generationService;

    private GenerationController controller;

    @BeforeEach
    void setUp() {
        controller = new GenerationController(generationService);
    }

    @Test
    void testStartGeneration_Success() {
        // Given
        String userId = "test-user-123";

        // When
        String response = controller.startGeneration(userId);

        // Then
        assertNotNull(response);
        assertTrue(response.contains("Generation started for user: " + userId));
        assertTrue(response.contains("operation: "));
        
        // Verify that the service method was called
        verify(generationService, times(1)).generateArchitecture(
            org.mockito.ArgumentMatchers.anyString(),
            eq(userId),
            eq("Sample Request")
        );
    }

    @Test
    void testStartGeneration_WithNullUserId() {
        // Given
        String userId = null;

        // When
        String response = controller.startGeneration(userId);

        // Then
        assertNotNull(response);
        assertTrue(response.contains("Generation started for user: null"));
        assertTrue(response.contains("operation: "));
        
        verify(generationService, times(1)).generateArchitecture(
            org.mockito.ArgumentMatchers.anyString(),
            eq(userId),
            eq("Sample Request")
        );
    }

    @Test
    void testStartGeneration_WithEmptyUserId() {
        // Given
        String userId = "";

        // When
        String response = controller.startGeneration(userId);

        // Then
        assertNotNull(response);
        assertTrue(response.contains("Generation started for user: "));
        assertTrue(response.contains("operation: "));
        
        verify(generationService, times(1)).generateArchitecture(
            org.mockito.ArgumentMatchers.anyString(),
            eq(userId),
            eq("Sample Request")
        );
    }

    @Test
    void testStartGeneration_WithSpecialCharacters() {
        // Given
        String userId = "user@domain.com";

        // When
        String response = controller.startGeneration(userId);

        // Then
        assertNotNull(response);
        assertTrue(response.contains("Generation started for user: " + userId));
        assertTrue(response.contains("operation: "));
        
        verify(generationService, times(1)).generateArchitecture(
            org.mockito.ArgumentMatchers.anyString(),
            eq(userId),
            eq("Sample Request")
        );
    }

    @Test
    void testStartGeneration_ResponseFormat() {
        // Given
        String userId = "test-user-123";

        // When
        String response = controller.startGeneration(userId);

        // Then
        assertNotNull(response);
        String[] parts = response.split(", operation: ");
        assertEquals(2, parts.length);
        assertEquals("Generation started for user: " + userId, parts[0]);
        assertTrue(parts[1].length() > 0); // UUID should not be empty
    }
} 