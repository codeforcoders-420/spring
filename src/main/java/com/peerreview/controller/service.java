// ProgressService.java
package com.yourpackage.service;

import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class ProgressService {
    private final SimpMessagingTemplate messagingTemplate;

    public ProgressService(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    public void sendProgressUpdate(double percentage) {
        messagingTemplate.convertAndSend("/topic/progress", percentage); // Send the percentage to the topic
    }
}
