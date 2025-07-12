package com.hicham.backend.dto.websocket;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO for WebSocket user join session messages
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserJoinSessionDTO {
    private Long projectId;
    private String userId;
    private String username;
    private String action; // JOIN, LEAVE
} 