package com.hicham.backend.controller;

import com.hicham.backend.service.WebSocketProgressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller;

@Controller
public class ProgressController {


    // Handle subscription requests with userId
    @MessageMapping("/subscribe")
    @SendToUser("/queue/progress")
    public String subscribeToProgress(@Payload String userId) {
        return "Subscribed to progress updates for user: " + userId;
    }
}
