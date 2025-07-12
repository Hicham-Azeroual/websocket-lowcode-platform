package com.hicham.backend.controller;

import com.hicham.backend.model.GenerationRequest;
import com.hicham.backend.service.ArchitectureGenerationService;
import com.hicham.backend.service.WebSocketProgressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Controller
@RestController
@RequestMapping("/api/progress")
public class ProgressController {
    private final WebSocketProgressService progressService;
    private final ArchitectureGenerationService generationService;

    @Autowired
    public ProgressController(WebSocketProgressService progressService, ArchitectureGenerationService generationService) {
        this.progressService = progressService;
        this.generationService = generationService;
    }

    @MessageMapping("/subscribe")
    @SendToUser("/queue/progress")
    public String subscribeToProgress(Principal user) {
        return "Subscribed to progress updates for user: " + user.getName();
    }

    @PostMapping("/start")
    public String startGeneration(@RequestParam String userId, @RequestParam String operationId, @RequestBody GenerationRequest request) {
        System.out.println("/api/progress/start called with userId=" + userId + ", operationId=" + operationId + ", request=" + request);
        generationService.generateArchitecture(operationId, userId, request);
        return "Generation started for user: " + userId + ", operation: " + operationId;
    }
} 