package com.hicham.backend.service;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import org.mockito.Mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ArchitectureGenerationServiceTest {

    @Mock
    private WebSocketProgressService progressService;

    private ArchitectureGenerationService generationService;

    @BeforeEach
    void setUp() {
        generationService = new ArchitectureGenerationService(progressService, 0); // no sleep in tests
    }

    @Test
    void testGenerateArchitecture_Success() throws ExecutionException, InterruptedException {
        // Given
        String operationId = "test-op-123";
        String userId = "test-user-123";
        String request = "Sample Request";

        // When
        CompletableFuture<Void> future = generationService.generateArchitecture(operationId, userId, request);

        // Then
        assertNotNull(future);
        future.get(); // Wait for completion
        
        // Verify that progress updates were sent
        // Total calls: 1 start + 5 progress + 1 completion = 7 calls
        verify(progressService, times(7)).sendProgressUpdate(
            eq(userId),
            any(com.hicham.backend.model.ProgressUpdate.class)
        );
        
        // System broadcasts: 1 start + 1 completion = 2 calls
        verify(progressService, times(2)).broadcastSystemUpdate(
            any(com.hicham.backend.model.ProgressUpdate.class)
        );
    }

    @Test
    void testGenerateArchitecture_WithNullParameters() throws ExecutionException, InterruptedException {
        // Given
        String operationId = null;
        String userId = null;
        String request = null;

        // When
        CompletableFuture<Void> future = generationService.generateArchitecture(operationId, userId, request);

        // Then
        assertNotNull(future);
        future.get(); // Wait for completion
        
        // Verify that progress updates were sent even with null parameters
        // Total calls: 1 start + 5 progress + 1 completion = 7 calls
        verify(progressService, times(7)).sendProgressUpdate(
            eq(userId),
            any(com.hicham.backend.model.ProgressUpdate.class)
        );
    }

    @Test
    void testGenerateArchitecture_WithEmptyParameters() throws ExecutionException, InterruptedException {
        // Given
        String operationId = "";
        String userId = "";
        String request = "";

        // When
        CompletableFuture<Void> future = generationService.generateArchitecture(operationId, userId, request);

        // Then
        assertNotNull(future);
        future.get(); // Wait for completion
        
        // Verify that progress updates were sent even with empty parameters
        // Total calls: 1 start + 5 progress + 1 completion = 7 calls
        verify(progressService, times(7)).sendProgressUpdate(
            eq(userId),
            any(com.hicham.backend.model.ProgressUpdate.class)
        );
    }

    @Test
    void testGenerateArchitecture_WithSpecialCharacters() throws ExecutionException, InterruptedException {
        // Given
        String operationId = "op-émojis-🚀";
        String userId = "user@domain.com";
        String request = "Request with special chars: émojis 🚀";

        // When
        CompletableFuture<Void> future = generationService.generateArchitecture(operationId, userId, request);

        // Then
        assertNotNull(future);
        future.get(); // Wait for completion
        
        // Verify that progress updates were sent
        // Total calls: 1 start + 5 progress + 1 completion = 7 calls
        verify(progressService, times(7)).sendProgressUpdate(
            eq(userId),
            any(com.hicham.backend.model.ProgressUpdate.class)
        );
    }

    @Test
    void testGenerateArchitecture_AsyncExecution() {
        // Given
        String operationId = "test-op-123";
        String userId = "test-user-123";
        String request = "Sample Request";

        // When
        CompletableFuture<Void> future = generationService.generateArchitecture(operationId, userId, request);

        // Then
        assertNotNull(future);
        // The method is async but completes quickly due to the 3-second sleep per step
        // We just verify that a future is returned
        assertTrue(future instanceof CompletableFuture, "Should return a CompletableFuture");
    }

    @Test
    void testGenerateArchitecture_ProgressSteps() throws ExecutionException, InterruptedException {
        // Given
        String operationId = "test-op-123";
        String userId = "test-user-123";
        String request = "Sample Request";

        // When
        CompletableFuture<Void> future = generationService.generateArchitecture(operationId, userId, request);
        future.get(); // Wait for completion

        // Then
        // Verify that we have the expected number of progress updates:
        // 1 start update + 5 progress updates + 1 completion update = 7 total
        verify(progressService, times(7)).sendProgressUpdate(
            eq(userId),
            any(com.hicham.backend.model.ProgressUpdate.class)
        );
        
        // Verify system broadcasts:
        // 1 start broadcast + 1 completion broadcast = 2 total
        verify(progressService, times(2)).broadcastSystemUpdate(
            any(com.hicham.backend.model.ProgressUpdate.class)
        );
    }
} 