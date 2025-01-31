package com.example.demo02.websocket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Component
public class LeaderBoardWebSocketHandler extends TextWebSocketHandler {

    private static final Logger logger = LoggerFactory.getLogger(LeaderBoardWebSocketHandler.class);
    private static final List<WebSocketSession> sessions = new CopyOnWriteArrayList<>(); // thread-safe collection

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        String userName = (String) session.getAttributes().get("user_name");
        if (userName == null) {
            logger.error("user_name is null for session: {}", session.getId());
            session.close(CloseStatus.BAD_DATA);
            return;
        }
        sessions.add(session); // Add the session when a client connects
        logger.info("User '{}' connected, session ID: {}", userName, session.getId());
    }

    @Override
    public void handleMessage(WebSocketSession session, org.springframework.web.socket.WebSocketMessage<?> message) throws Exception {
        // Handle incoming messages if needed (currently no functionality for incoming messages)
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        logger.error("Error in WebSocket session, ID: {}", session.getId(), exception);
        session.close();
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        String userName = (String) session.getAttributes().get("user_name");
        if (userName != null) {
            sessions.remove(userName);
            logger.info("User '{}' disconnected", userName);
        }
    }

    @Override
    public boolean supportsPartialMessages() {
        return false;
    }

    // Method to send leaderboard updates to all connected clients
    public void sendLeaderboardUpdateNotification(String message) {
        for (WebSocketSession session : sessions) {
            try {
                session.sendMessage(new TextMessage(message));
            } catch (Exception e) {
                logger.error("Error sending message to session {}: {}", session.getId(), e.getMessage());
            }
        }
    }

}
