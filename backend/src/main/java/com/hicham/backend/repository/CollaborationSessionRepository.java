package com.hicham.backend.repository;

import com.hicham.backend.entity.CollaborationSession;
import com.hicham.backend.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository for CollaborationSession entity operations
 */
@Repository
public interface CollaborationSessionRepository extends JpaRepository<CollaborationSession, Long> {
    
    /**
     * Find active sessions by project
     */
    List<CollaborationSession> findByProjectAndIsActiveTrue(Project project);
    
    /**
     * Find active session by project ID
     */
    Optional<CollaborationSession> findByProjectIdAndIsActiveTrue(Long projectId);
    
    /**
     * Find all sessions by project
     */
    List<CollaborationSession> findByProject(Project project);
} 