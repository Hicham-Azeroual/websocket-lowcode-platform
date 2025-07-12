package com.hicham.backend.service;


import com.hicham.backend.model.ProgressType;
import com.hicham.backend.model.ProgressUpdate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.concurrent.CompletableFuture;

@Service
public class ArchitectureGenerationService {
    private final WebSocketProgressService progressService;
    private static final int TOTAL_STEPS = 5; // Number of steps in simulation

    @Autowired
    public ArchitectureGenerationService(WebSocketProgressService progressService) {
        this.progressService = progressService;
    }

    // Simulate asynchronous architecture generation for a specific user
    @Async
    public CompletableFuture<Void> generateArchitecture(String operationId, String userId, String request) {
        try {
            // Send private start update to user
            ProgressUpdate startUpdate = new ProgressUpdate(
                    operationId, userId, ProgressType.GENERATION_STARTED, 0,
                    "Initialisation de la génération pour " + userId, "init", LocalDateTime.now()
            );
            progressService.sendProgressUpdate(userId, startUpdate);

            // Send public system update
            progressService.broadcastSystemUpdate(new ProgressUpdate(
                    operationId, userId, ProgressType.GENERATION_STARTED, 0,
                    "System: Generation started for " + userId, "init", LocalDateTime.now()
            ));

            // Simulate generation steps
            for (int i = 1; i <= TOTAL_STEPS; i++) {
                Thread.sleep(3000); // Simulate work (1 second per step)
                int percentage = (i * 100) / TOTAL_STEPS;
                // Send private progress update
                ProgressUpdate progressUpdate = new ProgressUpdate(
                        operationId, userId, ProgressType.GENERATION_PROGRESS, percentage,
                        "Etape " + i + " complétée pour " + userId, "step_" + i, LocalDateTime.now()
                );
                progressService.sendProgressUpdate(userId, progressUpdate);
            }

            // Send private completion update
            ProgressUpdate completeUpdate = new ProgressUpdate(
                    operationId, userId, ProgressType.GENERATION_COMPLETED, 100,
                    "Génération terminée avec succès pour " + userId, "completed", LocalDateTime.now()
            );
            progressService.sendProgressUpdate(userId, completeUpdate);

            // Send public completion update
            progressService.broadcastSystemUpdate(new ProgressUpdate(
                    operationId, userId, ProgressType.GENERATION_COMPLETED, 100,
                    "System: Generation completed for " + userId, "completed", LocalDateTime.now()
            ));

            return CompletableFuture.completedFuture(null);
        } catch (Exception e) {
           // progressService.sendErrorUpdate(userId, e, operationId);
           progressService.sendProgressUpdate(
            userId,
            new ProgressUpdate(
                operationId,
                userId,
                ProgressType.GENERATION_ERROR,
                null,
                "error",
                "An error occurred: " + e.getMessage(),
                LocalDateTime.now()
            )
        );
            return CompletableFuture.completedFuture(null);
        }
    }
}