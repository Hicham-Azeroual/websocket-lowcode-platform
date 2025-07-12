package com.hicham.backend.service;

import com.hicham.backend.dto.websocket.UserJoinSessionDTO;
import com.hicham.backend.entity.CollaborationSession;
import com.hicham.backend.entity.Project;
import com.hicham.backend.entity.User;
import com.hicham.backend.repository.CollaborationSessionRepository;
import com.hicham.backend.repository.ProjectRepository;
import com.hicham.backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service for managing real-time collaboration sessions
 */
@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class CollaborationService {
    
    private final CollaborationSessionRepository sessionRepository;
    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;
    private final SimpMessagingTemplate messagingTemplate;
    
    /**
     * Create or join a collaboration session
     */
    public CollaborationSession createOrJoinSession(Long projectId, Long userId) {
        log.info("User {} joining session for project {}", userId, projectId);
        
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new RuntimeException("Project not found"));
        
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        
        // Try to find existing active session
        Optional<CollaborationSession> existingSession = sessionRepository.findByProjectIdAndIsActiveTrue(projectId);
        
        CollaborationSession session;
        if (existingSession.isPresent()) {
            session = existingSession.get();
            if (!session.hasParticipant(user)) {
                session.addParticipant(user);
                session = sessionRepository.save(session);
                log.info("User {} added to existing session {}", userId, session.getId());
            }
        } else {
            // Create new session
            session = new CollaborationSession();
            session.setProject(project);
            session.setSessionName("Collaboration Session - " + project.getName());
            session.setIsActive(true);
            session.addParticipant(user);
            session = sessionRepository.save(session);
            log.info("New collaboration session created: {}", session.getId());
        }
        
        // Notify other participants
        notifyUserJoined(session, user);
        
        return session;
    }
    
    /**
     * Leave a collaboration session
     */
    public void leaveSession(Long projectId, Long userId) {
        log.info("User {} leaving session for project {}", userId, projectId);
        
        Optional<CollaborationSession> sessionOpt = sessionRepository.findByProjectIdAndIsActiveTrue(projectId);
        if (sessionOpt.isPresent()) {
            CollaborationSession session = sessionOpt.get();
            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new RuntimeException("User not found"));
            
            session.removeParticipant(user);
            
            // If no participants left, deactivate session
            if (session.getParticipants().isEmpty()) {
                session.setIsActive(false);
                log.info("Session {} deactivated - no participants left", session.getId());
            }
            
            sessionRepository.save(session);
            
            // Notify other participants
            notifyUserLeft(session, user);
        }
    }
    
    /**
     * Get active session for project
     */
    public Optional<CollaborationSession> getActiveSession(Long projectId) {
        return sessionRepository.findByProjectIdAndIsActiveTrue(projectId);
    }
    
    /**
     * Get all sessions for project
     */
    public List<CollaborationSession> getSessionsByProject(Long projectId) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new RuntimeException("Project not found"));
        return sessionRepository.findByProject(project);
    }
    
    /**
     * Send chat message to session participants
     */
    public void sendChatMessage(Long projectId, Long userId, String message) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        
        String destination = "/topic/project/" + projectId + "/chat";
        String payload = String.format("{\"userId\":\"%s\",\"username\":\"%s\",\"message\":\"%s\"}", 
                userId, user.getUsername(), message);
        
        messagingTemplate.convertAndSend(destination, payload);
        log.info("Chat message sent to project {} by user {}", projectId, userId);
    }
    
    /**
     * Notify other participants that a user joined
     */
    private void notifyUserJoined(CollaborationSession session, User user) {
        UserJoinSessionDTO joinDTO = new UserJoinSessionDTO(
                session.getProject().getId(),
                user.getId().toString(),
                user.getUsername(),
                "JOIN"
        );
        
        String destination = "/topic/project/" + session.getProject().getId() + "/users";
        messagingTemplate.convertAndSend(destination, joinDTO);
        log.info("User join notification sent for user: {}", user.getUsername());
    }
    
    /**
     * Notify other participants that a user left
     */
    private void notifyUserLeft(CollaborationSession session, User user) {
        UserJoinSessionDTO leaveDTO = new UserJoinSessionDTO(
                session.getProject().getId(),
                user.getId().toString(),
                user.getUsername(),
                "LEAVE"
        );
        
        String destination = "/topic/project/" + session.getProject().getId() + "/users";
        messagingTemplate.convertAndSend(destination, leaveDTO);
        log.info("User leave notification sent for user: {}", user.getUsername());
    }
} 