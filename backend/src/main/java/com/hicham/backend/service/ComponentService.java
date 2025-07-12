package com.hicham.backend.service;

import com.hicham.backend.dto.ComponentDTO;
import com.hicham.backend.dto.websocket.ComponentUpdateDTO;
import com.hicham.backend.entity.Component;
import com.hicham.backend.entity.Project;
import com.hicham.backend.repository.ComponentRepository;
import com.hicham.backend.repository.ProjectRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service for component management operations
 */
@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class ComponentService {
    
    private final ComponentRepository componentRepository;
    private final ProjectRepository projectRepository;
    private final SimpMessagingTemplate messagingTemplate;
    
    /**
     * Create a new component
     */
    public ComponentDTO createComponent(ComponentDTO componentDTO) {
        log.info("Creating new component: {} for project: {}", componentDTO.getType(), componentDTO.getProjectId());
        
        Project project = projectRepository.findById(componentDTO.getProjectId())
                .orElseThrow(() -> new RuntimeException("Project not found"));
        
        Component component = new Component();
        component.setType(componentDTO.getType());
        component.setDataJson(componentDTO.getDataJson());
        component.setPositionX(componentDTO.getPositionX());
        component.setPositionY(componentDTO.getPositionY());
        component.setWidth(componentDTO.getWidth());
        component.setHeight(componentDTO.getHeight());
        component.setProject(project);
        
        Component savedComponent = componentRepository.save(component);
        log.info("Component created successfully: {}", savedComponent.getId());
        
        ComponentDTO result = convertToDTO(savedComponent);
        
        // Notify other users via WebSocket
        notifyComponentUpdate(result, "CREATE");
        
        return result;
    }
    
    /**
     * Update a component
     */
    public ComponentDTO updateComponent(Long componentId, ComponentDTO componentDTO) {
        log.info("Updating component: {}", componentId);
        
        Component component = componentRepository.findById(componentId)
                .orElseThrow(() -> new RuntimeException("Component not found"));
        
        component.setType(componentDTO.getType());
        component.setDataJson(componentDTO.getDataJson());
        component.setPositionX(componentDTO.getPositionX());
        component.setPositionY(componentDTO.getPositionY());
        component.setWidth(componentDTO.getWidth());
        component.setHeight(componentDTO.getHeight());
        
        Component updatedComponent = componentRepository.save(component);
        log.info("Component updated successfully: {}", componentId);
        
        ComponentDTO result = convertToDTO(updatedComponent);
        
        // Notify other users via WebSocket
        notifyComponentUpdate(result, "UPDATE");
        
        return result;
    }
    
    /**
     * Delete a component
     */
    public void deleteComponent(Long componentId) {
        log.info("Deleting component: {}", componentId);
        
        Component component = componentRepository.findById(componentId)
                .orElseThrow(() -> new RuntimeException("Component not found"));
        
        Long projectId = component.getProject().getId();
        componentRepository.delete(component);
        log.info("Component deleted successfully: {}", componentId);
        
        // Notify other users via WebSocket
        ComponentDTO deletedComponent = convertToDTO(component);
        notifyComponentUpdate(deletedComponent, "DELETE");
    }
    
    /**
     * Get component by ID
     */
    public Optional<ComponentDTO> getComponentById(Long id) {
        return componentRepository.findById(id)
                .map(this::convertToDTO);
    }
    
    /**
     * Get components by project
     */
    public List<ComponentDTO> getComponentsByProject(Long projectId) {
        return componentRepository.findByProjectId(projectId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    /**
     * Get components by type
     */
    public List<ComponentDTO> getComponentsByType(String type) {
        return componentRepository.findByType(type).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    /**
     * Move component to new position
     */
    public ComponentDTO moveComponent(Long componentId, Double newX, Double newY) {
        log.info("Moving component: {} to position ({}, {})", componentId, newX, newY);
        
        Component component = componentRepository.findById(componentId)
                .orElseThrow(() -> new RuntimeException("Component not found"));
        
        component.setPositionX(newX);
        component.setPositionY(newY);
        
        Component movedComponent = componentRepository.save(component);
        log.info("Component moved successfully: {}", componentId);
        
        ComponentDTO result = convertToDTO(movedComponent);
        
        // Notify other users via WebSocket
        notifyComponentUpdate(result, "MOVE");
        
        return result;
    }
    
    /**
     * Notify other users about component updates via WebSocket
     */
    private void notifyComponentUpdate(ComponentDTO componentDTO, String action) {
        ComponentUpdateDTO updateDTO = new ComponentUpdateDTO(
                componentDTO.getId(),
                componentDTO.getType(),
                componentDTO.getDataJson(),
                componentDTO.getPositionX(),
                componentDTO.getPositionY(),
                componentDTO.getWidth(),
                componentDTO.getHeight(),
                action,
                null, // userId will be set by the controller
                null, // username will be set by the controller
                componentDTO.getProjectId()
        );
        
        String destination = "/topic/project/" + componentDTO.getProjectId() + "/components";
        messagingTemplate.convertAndSend(destination, updateDTO);
        log.info("Component update notification sent to: {}", destination);
    }
    
    /**
     * Convert Component entity to ComponentDTO
     */
    private ComponentDTO convertToDTO(Component component) {
        return new ComponentDTO(
                component.getId(),
                component.getType(),
                component.getDataJson(),
                component.getPositionX(),
                component.getPositionY(),
                component.getWidth(),
                component.getHeight(),
                component.getProject().getId(),
                component.getCreatedAt(),
                component.getUpdatedAt()
        );
    }
} 