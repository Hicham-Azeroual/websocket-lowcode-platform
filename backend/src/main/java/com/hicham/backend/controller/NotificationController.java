package com.hicham.backend.controller;


import com.hicham.backend.simulator.FakeProcessSimulator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * Contrôleur REST pour déclencher les opérations et notifications WebSocket
 */
@RestController
@RequestMapping("/api/operations")
@CrossOrigin(origins = "*") // Permet les requêtes CORS depuis le front-end React
public class NotificationController {
    
    private static final Logger log = LoggerFactory.getLogger(NotificationController.class);
    
    private final FakeProcessSimulator fakeProcessSimulator;

    public NotificationController(FakeProcessSimulator fakeProcessSimulator) {
        this.fakeProcessSimulator = fakeProcessSimulator;
    }

    /**
     * Endpoint POST qui déclenche une opération simulée
     * @return ResponseEntity avec un message de confirmation
     */
    @PostMapping("/start")
    public ResponseEntity<Map<String, Object>> startOperation() {
        log.info("Réception d'une demande de démarrage d'opération via POST /api/operations/start");
        
        try {
            // Démarre la simulation asynchrone
            fakeProcessSimulator.simulateOperation();
            
            Map<String, Object> response = Map.of(
                "status", "accepted",
                "message", "Opération démarrée avec succès. Écoutez les notifications WebSocket sur /topic/notifications",
                "timestamp", System.currentTimeMillis()
            );
            
            log.info("Opération démarrée avec succès");
            return ResponseEntity.accepted().body(response);
            
        } catch (Exception e) {
            log.error("Erreur lors du démarrage de l'opération", e);
            
            Map<String, Object> errorResponse = Map.of(
                "status", "error",
                "message", "Erreur lors du démarrage de l'opération: " + e.getMessage(),
                "timestamp", System.currentTimeMillis()
            );
            
            return ResponseEntity.internalServerError().body(errorResponse);
        }
    }

    /**
     * Endpoint GET pour vérifier l'état du service
     * @return ResponseEntity avec le statut du service
     */
    @GetMapping("/health")
    public ResponseEntity<Map<String, Object>> health() {
        Map<String, Object> response = Map.of(
            "status", "healthy",
            "service", "WebSocket Notification Service",
            "timestamp", System.currentTimeMillis()
        );
        
        return ResponseEntity.ok(response);
    }
}