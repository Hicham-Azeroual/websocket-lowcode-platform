package com.hicham.backend.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class ProgressUpdate {
    private String operationId;
    private String userId;
    private ProgressType type;
    private Integer percentage;
    private String step;
    private String message;
    private LocalDateTime timestamp;
} 