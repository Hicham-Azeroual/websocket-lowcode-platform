package com.hicham.backend.service;

import com.hicham.backend.model.ProgressUpdate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class WebSocketProgressService {
    private final SimpMessagingTemplate messagingTemplate;

    @Autowired
    public WebSocketProgressService(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    public void sendProgressUpdate(String userId, ProgressUpdate update) {
        messagingTemplate.convertAndSend("/topic/progress." + userId, update);
    }

    public void broadcastSystemUpdate(ProgressUpdate update) {
        messagingTemplate.convertAndSend("/topic/system", update);
    }
} 