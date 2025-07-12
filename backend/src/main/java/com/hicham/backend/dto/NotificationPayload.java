package com.hicham.backend.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.hicham.backend.model.OperationStatus;

/**
 * DTO représentant la structure du message JSON envoyé via WebSocket
 */
public class NotificationPayload {
    
    @JsonProperty("step")
    private OperationStatus step;
    
    @JsonProperty("message")
    private String message;
    
    @JsonProperty("timestamp")
    private long timestamp;

    public NotificationPayload() {
        this.timestamp = System.currentTimeMillis();
    }

    public NotificationPayload(OperationStatus step, String message) {
        this.step = step;
        // Handle null, empty, or whitespace messages by using default message
        if (message == null || message.trim().isEmpty()) {
            this.message = step.getDefaultMessage();
        } else {
            this.message = message;
        }
        this.timestamp = System.currentTimeMillis();
    }

    public OperationStatus getStep() {
        return step;
    }

    public void setStep(OperationStatus step) {
        this.step = step;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "NotificationPayload{" +
                "step=" + step +
                ", message='" + message + '\'' +
                ", timestamp=" + timestamp +
                '}';
    }
}