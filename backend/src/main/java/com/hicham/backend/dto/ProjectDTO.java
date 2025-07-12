package com.hicham.backend.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

/**
 * DTO for Project data transfer
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProjectDTO {
    private Long id;
    private String name;
    private String description;
    private UserDTO owner;
    private List<ComponentDTO> components;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
} 