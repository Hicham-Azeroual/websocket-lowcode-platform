package com.hicham.backend.service;

import com.hicham.backend.model.ProgressType;
import com.hicham.backend.model.ProgressUpdate;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
public class ArchitectureGenerationService {
    @Autowired
    private WebSocketProgressService progressService;

    @Async
    public CompletableFuture<Void> generateArchitecture(
            String operationId,
            String userId,
            Object request // Replace with your actual GenerationRequest type
    ) {
        int totalSteps = 5; // Example value, replace as needed
        try {
            // Start
            try {
                progressService.sendProgressUpdate(userId,
                    new ProgressUpdate(operationId, userId,
                        ProgressType.GENERATION_STARTED, 0,
                        "init", "Initialisation de la génération", LocalDateTime.now())
                );
            } catch (Exception e) {
                // Log error but continue
                System.err.println("Error sending start progress: " + e.getMessage());
            }

            // Progress steps
            for (int i = 0; i < totalSteps; i++) {
                // processStep(); // Your logic here
                int percentage = (i + 1) * 100 / totalSteps;
                try {
                    progressService.sendProgressUpdate(userId,
                        new ProgressUpdate(operationId, userId,
                            ProgressType.GENERATION_PROGRESS, percentage,
                            "step_" + i, "Étape " + (i + 1) + " complétée", LocalDateTime.now())
                    );
                } catch (Exception e) {
                    // Log error but continue
                    System.err.println("Error sending progress update: " + e.getMessage());
                }
            }

            // Completed
            try {
                progressService.sendProgressUpdate(userId,
                    new ProgressUpdate(operationId, userId,
                        ProgressType.GENERATION_COMPLETED, 100,
                        "completed", "Génération terminée avec succès", LocalDateTime.now())
                );
            } catch (Exception e) {
                // Log error but continue
                System.err.println("Error sending completion progress: " + e.getMessage());
            }
        } catch (Exception e) {
            try {
                progressService.sendProgressUpdate(userId,
                    new ProgressUpdate(operationId, userId,
                        ProgressType.GENERATION_ERROR, null,
                        "error", "Erreur: " + e.getMessage(), LocalDateTime.now())
                );
            } catch (Exception ex) {
                // Log error but continue
                System.err.println("Error sending error progress: " + ex.getMessage());
            }
        }
        return CompletableFuture.completedFuture(null);
    }
} 