package com.hicham.backend.controller;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.hicham.backend.simulator.FakeProcessSimulator;

@ExtendWith(MockitoExtension.class)
class NotificationControllerTest {

    @Mock
    private FakeProcessSimulator simulator;

    private NotificationController controller;

    @BeforeEach
    void setUp() {
        controller = new NotificationController(simulator);
    }

    @Test
    void testStartOperation_Success() {
        // Given
        doNothing().when(simulator).simulateOperation();

        // When
        ResponseEntity<Map<String, Object>> response = controller.startOperation();

        // Then
        assertNotNull(response);
        assertEquals(HttpStatus.ACCEPTED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Opération démarrée avec succès. Écoutez les notifications WebSocket sur /topic/notifications", response.getBody().get("message"));
        assertEquals("accepted", response.getBody().get("status"));
        
        verify(simulator, times(1)).simulateOperation();
    }

    @Test
    void testStartOperation_WhenSimulatorThrowsException() {
        // Given
        doThrow(new RuntimeException("Simulation error"))
            .when(simulator).simulateOperation();

        // When
        ResponseEntity<Map<String, Object>> response = controller.startOperation();

        // Then
        assertNotNull(response);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().get("message").toString().contains("Erreur lors du démarrage de l'opération"));
        assertEquals("error", response.getBody().get("status"));
        
        verify(simulator, times(1)).simulateOperation();
    }

    @Test
    void testHealth_Success() {
        // When
        ResponseEntity<Map<String, Object>> response = controller.health();

        // Then
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("healthy", response.getBody().get("status"));
        assertEquals("WebSocket Notification Service", response.getBody().get("service"));
    }

    @Test
    void testHealth_ResponseBodyContent() {
        // When
        ResponseEntity<Map<String, Object>> response = controller.health();

        // Then
        Map<String, Object> body = response.getBody();
        assertNotNull(body);
        assertTrue(body.containsKey("status"));
        assertTrue(body.containsKey("service"));
        assertTrue(body.containsKey("timestamp"));
        
        // Verify timestamp is a valid number
        assertDoesNotThrow(() -> {
            Long.parseLong(body.get("timestamp").toString());
        });
    }

    @Test
    void testStartOperation_ResponseBodyContent() {
        // Given
        doNothing().when(simulator).simulateOperation();

        // When
        ResponseEntity<Map<String, Object>> response = controller.startOperation();

        // Then
        Map<String, Object> body = response.getBody();
        assertNotNull(body);
        assertTrue(body.containsKey("message"));
        assertTrue(body.containsKey("status"));
        assertTrue(body.containsKey("timestamp"));
        
        assertEquals("Opération démarrée avec succès. Écoutez les notifications WebSocket sur /topic/notifications", body.get("message"));
        assertEquals("accepted", body.get("status"));
        
        // Verify timestamp is a valid number
        assertDoesNotThrow(() -> {
            Long.parseLong(body.get("timestamp").toString());
        });
    }

    @Test
    void testStartOperation_WhenSimulatorThrowsSpecificException() {
        // Given
        doThrow(new IllegalStateException("Simulator not ready"))
            .when(simulator).simulateOperation();

        // When
        ResponseEntity<Map<String, Object>> response = controller.startOperation();

        // Then
        assertNotNull(response);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().get("message").toString().contains("Erreur lors du démarrage de l'opération"));
        assertEquals("error", response.getBody().get("status"));
        
        verify(simulator, times(1)).simulateOperation();
    }

    @Test
    void testStartOperation_MultipleCalls() {
        // Given
        doNothing().when(simulator).simulateOperation();

        // When
        ResponseEntity<Map<String, Object>> response1 = controller.startOperation();
        ResponseEntity<Map<String, Object>> response2 = controller.startOperation();

        // Then
        assertNotNull(response1);
        assertNotNull(response2);
        assertEquals(HttpStatus.ACCEPTED, response1.getStatusCode());
        assertEquals(HttpStatus.ACCEPTED, response2.getStatusCode());
        
        verify(simulator, times(2)).simulateOperation();
    }

    @Test
    void testHealth_MultipleCalls() {
        // When
        ResponseEntity<Map<String, Object>> response1 = controller.health();
        
        // Add a small delay to ensure different timestamps
        try {
            Thread.sleep(1);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        ResponseEntity<Map<String, Object>> response2 = controller.health();

        // Then
        assertNotNull(response1);
        assertNotNull(response2);
        assertEquals(HttpStatus.OK, response1.getStatusCode());
        assertEquals(HttpStatus.OK, response2.getStatusCode());
        
        // Both responses should have the same status but different timestamps
        assertEquals("healthy", response1.getBody().get("status"));
        assertEquals("healthy", response2.getBody().get("status"));
        assertNotEquals(response1.getBody().get("timestamp"), response2.getBody().get("timestamp"));
    }
} 