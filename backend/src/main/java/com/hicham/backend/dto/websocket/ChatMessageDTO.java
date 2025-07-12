package com.hicham.backend.dto.websocket;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO for WebSocket chat messages
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChatMessageDTO {
    private Long projectId;
    private String userId;
    private String username;
    private String message;
    private LocalDateTime timestamp;
    private String messageType; // TEXT, SYSTEM, NOTIFICATION
} 