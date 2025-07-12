package com.hicham.backend.service;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import org.mockito.Mock;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import org.mockito.junit.jupiter.MockitoExtension;

import com.hicham.backend.model.ProgressType;

import java.util.concurrent.CompletableFuture;

@ExtendWith(MockitoExtension.class)
class ArchitectureGenerationServiceTest {

    @Mock
    private WebSocketProgressService progressService;

    private ArchitectureGenerationService architectureService;

    @BeforeEach
    void setUp() {
        architectureService = new ArchitectureGenerationService();
        // Use reflection to set the private field since it's autowired
        try {
            java.lang.reflect.Field field = ArchitectureGenerationService.class.getDeclaredField("progressService");
            field.setAccessible(true);
            field.set(architectureService, progressService);
        } catch (Exception e) {
            throw new RuntimeException("Failed to set up test", e);
        }
    }

    @Test
    void testGenerateArchitecture_Success() {
        // Given
        String operationId = "op-123";
        String userId = "user-456";
        Object request = new Object();
        doNothing().when(progressService).sendProgressUpdate(eq(userId), any());

        // When
        CompletableFuture<Void> future = architectureService.generateArchitecture(operationId, userId, request);

        // Then
        assertNotNull(future);
        assertDoesNotThrow(() -> future.get());
        
        // Verify progress updates were sent
        verify(progressService, times(7)).sendProgressUpdate(eq(userId), any());
    }

    @Test
    void testGenerateArchitecture_WithNullRequest() {
        // Given
        String operationId = "op-123";
        String userId = "user-456";
        Object request = null;
        doNothing().when(progressService).sendProgressUpdate(eq(userId), any());

        // When
        CompletableFuture<Void> future = architectureService.generateArchitecture(operationId, userId, request);

        // Then
        assertNotNull(future);
        assertDoesNotThrow(() -> future.get());
        
        // Verify progress updates were sent
        verify(progressService, times(7)).sendProgressUpdate(eq(userId), any());
    }

    @Test
    void testGenerateArchitecture_WhenProgressServiceThrowsException() {
        // Given
        String operationId = "op-123";
        String userId = "user-456";
        Object request = new Object();
        doThrow(new RuntimeException("Progress service error"))
            .when(progressService).sendProgressUpdate(eq(userId), any());

        // When
        CompletableFuture<Void> future = architectureService.generateArchitecture(operationId, userId, request);

        // Then
        assertNotNull(future);
        // The service should handle exceptions gracefully and still complete
        assertDoesNotThrow(() -> future.get());
        
        // Should still attempt to send progress updates despite exceptions
        verify(progressService, times(7)).sendProgressUpdate(eq(userId), any());
    }

    @Test
    void testGenerateArchitecture_WithEmptyOperationId() {
        // Given
        String operationId = "";
        String userId = "user-456";
        Object request = new Object();
        doNothing().when(progressService).sendProgressUpdate(eq(userId), any());

        // When
        CompletableFuture<Void> future = architectureService.generateArchitecture(operationId, userId, request);

        // Then
        assertNotNull(future);
        assertDoesNotThrow(() -> future.get());
        
        // Verify progress updates were sent
        verify(progressService, times(7)).sendProgressUpdate(eq(userId), any());
    }

    @Test
    void testGenerateArchitecture_WithEmptyUserId() {
        // Given
        String operationId = "op-123";
        String userId = "";
        Object request = new Object();
        doNothing().when(progressService).sendProgressUpdate(eq(userId), any());

        // When
        CompletableFuture<Void> future = architectureService.generateArchitecture(operationId, userId, request);

        // Then
        assertNotNull(future);
        assertDoesNotThrow(() -> future.get());
        
        // Verify progress updates were sent
        verify(progressService, times(7)).sendProgressUpdate(eq(userId), any());
    }

    @Test
    void testGenerateArchitecture_WithSpecialCharactersInIds() {
        // Given
        String operationId = "op-123@domain.com";
        String userId = "user-456@test.org";
        Object request = new Object();
        doNothing().when(progressService).sendProgressUpdate(eq(userId), any());

        // When
        CompletableFuture<Void> future = architectureService.generateArchitecture(operationId, userId, request);

        // Then
        assertNotNull(future);
        assertDoesNotThrow(() -> future.get());
        
        // Verify progress updates were sent
        verify(progressService, times(7)).sendProgressUpdate(eq(userId), any());
    }
} 