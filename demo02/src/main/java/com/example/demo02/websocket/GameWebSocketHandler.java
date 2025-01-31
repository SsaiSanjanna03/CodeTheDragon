package com.example.demo02.websocket;

import org.springframework.web.socket.*;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentHashMap;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.atomic.AtomicBoolean;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo02.controller.LeaderBoardController;

@Component
public class GameWebSocketHandler extends TextWebSocketHandler {

    private static final Logger logger = LoggerFactory.getLogger(GameWebSocketHandler.class);
    private static final Map<String, WebSocketSession> sessions = new ConcurrentHashMap<>();
    private static String waitingUser = null;
    private static final int TOTAL_QUESTIONS = 10;
    private static final int BONUS_POINTS_FOR_WINNER = 50;
    private static final Map<String, Integer> userScores = new ConcurrentHashMap<>();
    private static final Map<String, Integer> userQuestionCount = new ConcurrentHashMap<>();
    private static final Map<String, Boolean> answerStatus = new ConcurrentHashMap<>();
    private static final ReentrantLock lock = new ReentrantLock();
    private static final AtomicBoolean gameInProgress = new AtomicBoolean(false);

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private LeaderBoardController leaderBoardController;

    @Override
    public void afterConnectionEstablished(@NonNull WebSocketSession session) throws Exception {
        String userName = (String) session.getAttributes().get("user_name");

        if (userName == null) {
            logger.error("user_name is null for session: {}", session.getId());
            session.close(CloseStatus.BAD_DATA);
            return;
        }

        lock.lock();
        try {
            sessions.put(userName, session);

            if (waitingUser == null) {
                waitingUser = userName;
                session.sendMessage(new TextMessage("Waiting for an opponent..."));
                logger.info("{} is waiting for an opponent.", userName);
            } else {
                WebSocketSession opponentSession = sessions.get(waitingUser);

                if (opponentSession != null && opponentSession.isOpen()) {
                    session.sendMessage(new TextMessage("Player 2 joined"));
                    session.sendMessage(new TextMessage("Game started! Opponent: " + waitingUser));
                    opponentSession.sendMessage(new TextMessage("Game started! Opponent: " + userName));

                    userScores.put(userName, 0);
                    userScores.put(waitingUser, 0);
                    userQuestionCount.put(userName, 0);
                    userQuestionCount.put(waitingUser, 0);
                    answerStatus.put(userName, false);
                    answerStatus.put(waitingUser, false);

                    gameInProgress.set(true);
                    sendNextQuestion(session, opponentSession);

                    logger.info("Game started between {} and {}", userName, waitingUser);
                    waitingUser = null;
                } else {
                    waitingUser = userName;
                    session.sendMessage(new TextMessage("Waiting for an opponent..."));
                    logger.info("{} is now waiting for an opponent.", userName);
                }
            }
        } finally {
            lock.unlock();
        }

        logger.info("{} connected.", userName);
    }

    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String userName = (String) session.getAttributes().get("user_name");
        logger.info("Message from " + userName + ": " + message.getPayload());

        lock.lock();
        try {
            if (!gameInProgress.get()) {
                session.sendMessage(new TextMessage("Game is not in progress."));
                return;
            }

            if (answerStatus.getOrDefault(userName, false)) {
                session.sendMessage(new TextMessage("Please wait for your opponent to answer."));
                return;
            }

            if ("correct".equalsIgnoreCase(message.getPayload())) {
                userScores.put(userName, userScores.getOrDefault(userName, 0) + 10);
                session.sendMessage(new TextMessage("Answer recorded as correct."));
            } else {
                session.sendMessage(new TextMessage("Answer recorded as incorrect."));
            }

            answerStatus.put(userName, true);
            userQuestionCount.put(userName, userQuestionCount.getOrDefault(userName, 0) + 1);

            if (bothPlayersAnswered()) {
                if (bothPlayersCompletedAllQuestions()) {
                    String user1 = null, user2 = null;
                    for (String user : userScores.keySet()) {
                        if (user1 == null) {
                            user1 = user;
                        } else {
                            user2 = user;
                            break;
                        }
                    }

                    if (user1 != null && user2 != null) {
                        WebSocketSession session1 = sessions.get(user1);
                        WebSocketSession session2 = sessions.get(user2);

                        // Only call endGame if both sessions are valid
                        if (session1 != null && session2 != null) {
                            endGame(session1, session2);
                        } else {
                            logger.error("One or both sessions are null; cannot end game.");
                        }
                    }
                } else {
                    String user1 = null, user2 = null;
                    for (String user : userScores.keySet()) {
                        if (user1 == null) {
                            user1 = user;
                        } else {
                            user2 = user;
                            break;
                        }
                    }

                    WebSocketSession session1 = sessions.get(user1);
                    WebSocketSession session2 = sessions.get(user2);
                    resetAnswerStatus();
                    sendNextQuestion(session1, session2);
                }
            }
        } finally {
            lock.unlock();
        }
    }



    private boolean bothPlayersAnswered() {
        return answerStatus.values().stream().allMatch(Boolean::booleanValue);
    }

    private void resetAnswerStatus() {
        answerStatus.replaceAll((k, v) -> false);
    }

    private boolean bothPlayersCompletedAllQuestions() {
        return userQuestionCount.values().stream().allMatch(count -> count == TOTAL_QUESTIONS);
    }

    private void sendNextQuestion(WebSocketSession session1, WebSocketSession session2) throws Exception {
        if (session1 != null && session1.isOpen()) {
            session1.sendMessage(new TextMessage("Next question"));
        }
        if (session2 != null && session2.isOpen()) {
            session2.sendMessage(new TextMessage("Next question"));
        }
    }

    @Transactional
    private void endGame(WebSocketSession session1, WebSocketSession session2) {
        if (session1 == null || session2 == null) {
            logger.error("One or both sessions are null; cannot end game.");
            return;
        }

        try {
            String user1 = (String) session1.getAttributes().get("user_name");
            String user2 = (String) session2.getAttributes().get("user_name");

            if (user1 == null || user2 == null) {
                logger.error("Both user1 and user2 must have valid names to end the game.");
                return;
            }

            int score1 = userScores.getOrDefault(user1, 0);
            int score2 = userScores.getOrDefault(user2, 0);

            if (score1 > score2) {
                jdbcTemplate.update("UPDATE users SET tot_points = tot_points + ? + ? WHERE user_name = ?", score1, BONUS_POINTS_FOR_WINNER, user1);
            }
            if (score2 > score1) {
                jdbcTemplate.update("UPDATE users SET tot_points = tot_points + ? + ? WHERE user_name = ?", score2, BONUS_POINTS_FOR_WINNER, user2);
            }

            String resultMessage = (score1 > score2) ? user1 + " wins!" : (score2 > score1 ? user2 + " wins!" : "Game ended in a tie.");
            leaderBoardController.updateLeaderboard();
            sendMessageIfOpen(session1, resultMessage);
            sendMessageIfOpen(session2, resultMessage);

            logger.info("Game ended with scores - {}: {}, {}: {}", user1, score1, user2, score2);

        } catch (Exception e) {
            logger.error("Error in endGame: {}", e.getMessage(), e);
        } finally {
            resetGameState();
        }
    }


    private void sendMessageIfOpen(WebSocketSession session, String message) {
        try {
            if (session != null && session.isOpen()) {
                session.sendMessage(new TextMessage(message));
            }
        } catch (Exception e) {
            logger.error("Failed to send message: {}", e.getMessage(), e);
        }
    }

    @Override
    public void afterConnectionClosed(@NonNull WebSocketSession session, @NonNull CloseStatus status) {
        String userName = (String) session.getAttributes().get("user_name");
        sessions.remove(userName);

        if (gameInProgress.get()) {
            try {
                handleDisconnection(session);
            } catch (Exception e) {
                logger.error("Error handling player disconnection: {}", e.getMessage(), e);
            }
        }
    }

    private void handleDisconnection(WebSocketSession session) {
        WebSocketSession opponentSession = sessions.values().stream().findFirst().orElse(null);

        if (opponentSession != null && opponentSession.isOpen()) {
            try {
                opponentSession.sendMessage(new TextMessage("Your opponent disconnected. Waiting for another."));
            } catch (Exception e) {
                logger.error("Failed to notify opponent about disconnection: {}", e.getMessage(), e);
            }
        }
    }

    private void resetGameState() {
        userScores.clear();
        userQuestionCount.clear();
        answerStatus.clear();
        gameInProgress.set(false);
    }
}
