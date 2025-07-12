package com.hicham.backend.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO for Component data transfer
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ComponentDTO {
    private Long id;
    private String type;
    private String dataJson;
    private Double positionX;
    private Double positionY;
    private Double width;
    private Double height;
    private Long projectId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
} 