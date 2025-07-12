package com.hicham.backend.repository;

import com.hicham.backend.entity.Component;
import com.hicham.backend.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository for Component entity operations
 */
@Repository
public interface ComponentRepository extends JpaRepository<Component, Long> {
    
    /**
     * Find components by project
     */
    List<Component> findByProject(Project project);
    
    /**
     * Find components by project ID
     */
    List<Component> findByProjectId(Long projectId);
    
    /**
     * Find components by type
     */
    List<Component> findByType(String type);
    
    /**
     * Find components by project and type
     */
    List<Component> findByProjectAndType(Project project, String type);
} 