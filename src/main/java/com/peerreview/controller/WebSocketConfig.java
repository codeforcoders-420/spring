// WebSocketConfig.java
package com.yourpackage.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.subscribers.Subscribers;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/progress").withSockJS(); // Endpoint for WebSocket connection
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.enableSimpleBroker("/topic"); // Destination prefix for sending messages
        config.setApplicationDestinationPrefixes("/app"); // Prefix for messages sent from client
    }
}
