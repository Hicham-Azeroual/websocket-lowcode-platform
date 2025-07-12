package com.hicham.backend.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProgressUpdate {
    private String operationId;
    private String userId;
    private ProgressType type;
    private Integer percentage;
    private String message;
    private String status;
    private LocalDateTime timestamp;

    public ProgressUpdate() {}
} 