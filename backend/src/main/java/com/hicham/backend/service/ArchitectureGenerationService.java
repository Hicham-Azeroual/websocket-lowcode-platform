package com.hicham.backend.service;

import com.hicham.backend.model.GenerationRequest;
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
    private static final int TOTAL_STEPS = 5;

    @Autowired
    public ArchitectureGenerationService(WebSocketProgressService progressService) {
        this.progressService = progressService;
    }

    @Async
    public CompletableFuture<Void> generateArchitecture(String operationId, String userId, GenerationRequest request) {
        System.out.println("generateArchitecture called with operationId=" + operationId + ", userId=" + userId + ", request=" + request);
        try {
            progressService.sendProgressUpdate(userId, new ProgressUpdate(
                operationId, userId, ProgressType.GENERATION_STARTED, 0,
                "Initialisation de la génération", "init", LocalDateTime.now()
            ));

            for (int i = 0; i < TOTAL_STEPS; i++) {
                Thread.sleep(1000);
                int percentage = (i + 1) * 100 / TOTAL_STEPS;
                progressService.sendProgressUpdate(userId, new ProgressUpdate(
                    operationId, userId, ProgressType.GENERATION_PROGRESS, percentage,
                    "Étape " + (i + 1) + " complétée", "step_" + i, LocalDateTime.now()
                ));
            }

            progressService.sendProgressUpdate(userId, new ProgressUpdate(
                operationId, userId, ProgressType.GENERATION_COMPLETED, 100,
                "Génération terminée avec succès", "completed", LocalDateTime.now()
            ));
        } catch (Exception e) {
            progressService.sendProgressUpdate(userId, new ProgressUpdate(
                operationId, userId, ProgressType.GENERATION_ERROR, null,
                "Error: " + e.getMessage(), "error", LocalDateTime.now()
            ));
        }
        return CompletableFuture.completedFuture(null);
    }
} 