package com.hicham.backend.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import com.hicham.backend.dto.NotificationPayload;
import com.hicham.backend.model.OperationStatus;

/**
 * Service responsable de l'envoi des notifications WebSocket
 */
@Service
public class NotificationService {
    
    private static final Logger log = LoggerFactory.getLogger(NotificationService.class);
    private static final String NOTIFICATION_TOPIC = "/topic/notifications";
    
    private final SimpMessagingTemplate messagingTemplate;

    public NotificationService(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    /**
     * Envoie une notification WebSocket avec le statut et le message fournis
     * @param status Le statut de l'opération
     * @param message Le message à envoyer
     */
    public void sendNotification(OperationStatus status, String message) {
        // Handle null, empty, or whitespace messages by using default message
        String finalMessage = (message == null || message.trim().isEmpty()) 
            ? status.getDefaultMessage() 
            : message;
            
        NotificationPayload payload = new NotificationPayload(status, finalMessage);
        
        log.info("Envoi de notification WebSocket: {}", payload);
        
        messagingTemplate.convertAndSend(NOTIFICATION_TOPIC, payload);
        
        log.info("Notification envoyée avec succès sur le topic: {}", NOTIFICATION_TOPIC);
    }

    /**
     * Envoie une notification WebSocket avec le message par défaut du statut
     * @param status Le statut de l'opération
     */
    public void sendNotification(OperationStatus status) {
        sendNotification(status, status.getDefaultMessage());
    }
}