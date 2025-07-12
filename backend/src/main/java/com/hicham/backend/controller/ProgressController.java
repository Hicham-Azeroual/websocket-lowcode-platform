package com.hicham.backend.controller;

import com.hicham.backend.service.WebSocketProgressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

import java.security.Principal;

@Controller
public class ProgressController {
    @Autowired
    private WebSocketProgressService progressService;

    @MessageMapping("/subscribe")
    public String subscribeToProgress(Principal user) {
        return "Subscribed to progress updates";
    }
} 