package com.hicham.backend.service;

import com.hicham.backend.dto.ProjectDTO;
import com.hicham.backend.entity.Project;
import com.hicham.backend.entity.User;
import com.hicham.backend.repository.ProjectRepository;
import com.hicham.backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service for project management operations
 */
@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class ProjectService {
    
    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;
    
    /**
     * Create a new project
     */
    public ProjectDTO createProject(String name, String description, Long ownerId) {
        log.info("Creating new project: {} for user: {}", name, ownerId);
        
        User owner = userRepository.findById(ownerId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        
        Project project = new Project();
        project.setName(name);
        project.setDescription(description);
        project.setOwner(owner);
        
        Project savedProject = projectRepository.save(project);
        log.info("Project created successfully: {}", name);
        
        return convertToDTO(savedProject);
    }
    
    /**
     * Get project by ID
     */
    public Optional<ProjectDTO> getProjectById(Long id) {
        return projectRepository.findById(id)
                .map(this::convertToDTO);
    }
    
    /**
     * Get projects by owner
     */
    public List<ProjectDTO> getProjectsByOwner(Long ownerId) {
        return projectRepository.findByOwnerId(ownerId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    /**
     * Get all projects
     */
    public List<ProjectDTO> getAllProjects() {
        return projectRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    /**
     * Search projects by name
     */
    public List<ProjectDTO> searchProjectsByName(String name) {
        return projectRepository.findByNameContainingIgnoreCase(name).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    /**
     * Update project
     */
    public ProjectDTO updateProject(Long projectId, String name, String description) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new RuntimeException("Project not found"));
        
        project.setName(name);
        project.setDescription(description);
        
        Project updatedProject = projectRepository.save(project);
        log.info("Project updated successfully: {}", name);
        
        return convertToDTO(updatedProject);
    }
    
    /**
     * Delete project
     */
    public void deleteProject(Long projectId) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new RuntimeException("Project not found"));
        
        projectRepository.delete(project);
        log.info("Project deleted successfully: {}", project.getName());
    }
    
    /**
     * Convert Project entity to ProjectDTO
     */
    private ProjectDTO convertToDTO(Project project) {
        return new ProjectDTO(
                project.getId(),
                project.getName(),
                project.getDescription(),
                null, // Owner DTO would be set here if needed
                null, // Components DTOs would be set here if needed
                project.getCreatedAt(),
                project.getUpdatedAt()
        );
    }
} 