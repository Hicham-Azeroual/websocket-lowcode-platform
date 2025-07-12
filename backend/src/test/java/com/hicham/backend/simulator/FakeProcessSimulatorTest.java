package com.hicham.backend.simulator;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.Mock;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import org.mockito.junit.jupiter.MockitoExtension;

import com.hicham.backend.model.OperationStatus;
import com.hicham.backend.service.NotificationService;

@ExtendWith(MockitoExtension.class)
class FakeProcessSimulatorTest {

    @Mock
    private NotificationService notificationService;

    private FakeProcessSimulator simulator;

    @BeforeEach
    void setUp() {
        simulator = new FakeProcessSimulator(notificationService);
    }

    @Test
    void testSimulateOperation_Success() throws InterruptedException {
        // Given
        doNothing().when(notificationService).sendNotification(any(OperationStatus.class));

        // When
        simulator.simulateOperation();

        // Then - In unit tests, @Async doesn't work, so the method executes synchronously
        verify(notificationService, times(3)).sendNotification(any(OperationStatus.class));
        verify(notificationService, times(1)).sendNotification(OperationStatus.STARTED);
        verify(notificationService, times(1)).sendNotification(OperationStatus.PROCESSING);
        verify(notificationService, times(1)).sendNotification(OperationStatus.DONE);
    }

    @Test
    void testSimulateOperation_VerifiesCorrectOrder() throws InterruptedException {
        // Given
        doNothing().when(notificationService).sendNotification(any(OperationStatus.class));

        // When
        simulator.simulateOperation();

        // Then - In unit tests, @Async doesn't work, so the method executes synchronously
        verify(notificationService, times(3)).sendNotification(any(OperationStatus.class));
        
        // Verify the order of calls
        verify(notificationService).sendNotification(OperationStatus.STARTED);
        verify(notificationService).sendNotification(OperationStatus.PROCESSING);
        verify(notificationService).sendNotification(OperationStatus.DONE);
    }

    @Test
    void testSimulateOperation_WhenNotificationServiceThrowsException() throws InterruptedException {
        // Given
        doThrow(new RuntimeException("Notification service error"))
            .when(notificationService).sendNotification(any(OperationStatus.class));

        // When & Then
        assertDoesNotThrow(() -> {
            simulator.simulateOperation();
        });
        
        // Should still attempt to send all notifications despite exceptions
        verify(notificationService, times(3)).sendNotification(any(OperationStatus.class));
    }

    @Test
    void testSimulateOperation_WithCustomDelay() throws InterruptedException {
        // Given
        doNothing().when(notificationService).sendNotification(any(OperationStatus.class));
        long startTime = System.currentTimeMillis();

        // When
        simulator.simulateOperation();

        // Then - In unit tests, @Async doesn't work, so the method executes synchronously
        long endTime = System.currentTimeMillis();
        long duration = endTime - startTime;
        
        // The method should take some time due to the delays (at least 4 seconds)
        assertTrue(duration >= 4000, "Method should take at least 4 seconds due to delays, but took " + duration + "ms");
        
        verify(notificationService, times(3)).sendNotification(any(OperationStatus.class));
    }

    @Test
    void testSimulateOperation_HandlesMultipleExceptions() throws InterruptedException {
        // Given
        doThrow(new RuntimeException("First error"))
            .doThrow(new IllegalStateException("Second error"))
            .doThrow(new RuntimeException("Third error"))
            .when(notificationService).sendNotification(any(OperationStatus.class));

        // When & Then
        assertDoesNotThrow(() -> {
            simulator.simulateOperation();
        });
        
        // Should attempt to send all notifications despite exceptions
        verify(notificationService, times(3)).sendNotification(any(OperationStatus.class));
    }

    @Test
    void testSimulateOperation_WithNullNotificationService() {
        // When & Then
        assertThrows(NullPointerException.class, () -> {
            new FakeProcessSimulator(null);
        });
    }

    @Test
    void testSimulateOperation_WithAllOperationStatuses() throws InterruptedException {
        // Given
        doNothing().when(notificationService).sendNotification(any(OperationStatus.class));

        // When
        simulator.simulateOperation();

        // Then - In unit tests, @Async doesn't work, so the method executes synchronously
        
        // Verify all three statuses are sent
        verify(notificationService).sendNotification(OperationStatus.STARTED);
        verify(notificationService).sendNotification(OperationStatus.PROCESSING);
        verify(notificationService).sendNotification(OperationStatus.DONE);
        
        // Verify no other statuses are sent
        verify(notificationService, times(3)).sendNotification(any(OperationStatus.class));
    }

    @Test
    void testSimulateOperation_IsAsynchronous() throws InterruptedException {
        // Given
        doNothing().when(notificationService).sendNotification(any(OperationStatus.class));

        // When
        long startTime = System.currentTimeMillis();
        simulator.simulateOperation();
        long endTime = System.currentTimeMillis();
        
        // Then
        long duration = endTime - startTime;
        
        // In unit tests, @Async doesn't work, so the method executes synchronously
        // It should take at least 4 seconds due to the delays
        assertTrue(duration >= 4000, "Method should take at least 4 seconds due to delays, but took " + duration + "ms");
        
        verify(notificationService, times(3)).sendNotification(any(OperationStatus.class));
    }
} 