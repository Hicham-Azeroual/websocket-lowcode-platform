package com.hicham.backend.simulator;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.hicham.backend.model.OperationStatus;
import com.hicham.backend.service.NotificationService;

/**
 * Simulateur d'opération longue en 3 étapes avec notifications WebSocket
 */
@Component
public class FakeProcessSimulator {
    
    private static final Logger log = LoggerFactory.getLogger(FakeProcessSimulator.class);
    private static final int DELAY_BETWEEN_STEPS_MS = 2000;
    
    private final NotificationService notificationService;

    public FakeProcessSimulator(NotificationService notificationService) {
        if (notificationService == null) {
            throw new NullPointerException("NotificationService cannot be null");
        }
        this.notificationService = notificationService;
    }

    /**
     * Simule une opération en 3 étapes avec des délais de 2 secondes entre chaque étape
     * Méthode asynchrone pour ne pas bloquer le thread principal
     */
    @Async
    public void simulateOperation() {
        try {
            log.info("Début de la simulation d'opération");
            
            // Étape 1 : Démarrage
            log.info("Étape 1/3 - Démarrage de l'opération");
            try {
                notificationService.sendNotification(OperationStatus.STARTED);
            } catch (Exception e) {
                log.error("Erreur lors de l'envoi de la notification STARTED", e);
            }
            
            try {
                Thread.sleep(DELAY_BETWEEN_STEPS_MS);
            } catch (InterruptedException e) {
                log.error("Simulation d'opération interrompue", e);
                Thread.currentThread().interrupt();
                return;
            }
            
            // Étape 2 : Traitement
            log.info("Étape 2/3 - Traitement en cours");
            try {
                notificationService.sendNotification(OperationStatus.PROCESSING);
            } catch (Exception e) {
                log.error("Erreur lors de l'envoi de la notification PROCESSING", e);
            }
            
            try {
                Thread.sleep(DELAY_BETWEEN_STEPS_MS);
            } catch (InterruptedException e) {
                log.error("Simulation d'opération interrompue", e);
                Thread.currentThread().interrupt();
                return;
            }
            
            // Étape 3 : Terminé
            log.info("Étape 3/3 - Opération terminée");
            try {
                notificationService.sendNotification(OperationStatus.DONE);
            } catch (Exception e) {
                log.error("Erreur lors de l'envoi de la notification DONE", e);
            }
            
            log.info("Simulation d'opération terminée avec succès");
            
        } catch (Exception e) {
            log.error("Erreur générale durant la simulation d'opération", e);
        }
    }
}