package com.hicham.backend.controller;

import com.hicham.backend.service.ArchitectureGenerationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
public class GenerationController {
    private final ArchitectureGenerationService generationService;

    @Autowired
    public GenerationController(ArchitectureGenerationService generationService) {
        this.generationService = generationService;
    }

    // Trigger generation for a specific user
    @GetMapping("/generate")
    public String startGeneration(@RequestParam String userId) {
        String operationId = UUID.randomUUID().toString();
        generationService.generateArchitecture(operationId, userId, "Sample Request");
        return "Generation started for user: " + userId + ", operation: " + operationId;
    }
}
