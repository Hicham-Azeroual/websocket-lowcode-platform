package com.hicham.backend.dto.websocket;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO for WebSocket component update messages
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ComponentUpdateDTO {
    private Long componentId;
    private String type;
    private String dataJson;
    private Double positionX;
    private Double positionY;
    private Double width;
    private Double height;
    private String action; // CREATE, UPDATE, DELETE, MOVE
    private String userId;
    private String username;
    private Long projectId;
} 