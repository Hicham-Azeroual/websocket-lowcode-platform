package com.hicham.backend.repository;

import com.hicham.backend.entity.Project;
import com.hicham.backend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository for Project entity operations
 */
@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {
    
    /**
     * Find projects by owner
     */
    List<Project> findByOwner(User owner);
    
    /**
     * Find projects by owner ID
     */
    List<Project> findByOwnerId(Long ownerId);
    
    /**
     * Find projects by name containing (case-insensitive)
     */
    List<Project> findByNameContainingIgnoreCase(String name);
} 