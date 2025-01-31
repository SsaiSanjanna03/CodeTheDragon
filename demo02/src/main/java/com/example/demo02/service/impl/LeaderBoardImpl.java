package com.example.demo02.service.impl;

import com.example.demo02.dto.LeaderBoardDTO;
import com.example.demo02.entity.User;
import com.example.demo02.repository.UserRepo;
import com.example.demo02.service.LeaderBoardService;
import com.example.demo02.websocket.LeaderBoardWebSocketHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LeaderBoardImpl implements LeaderBoardService {

    private static final Logger logger = LoggerFactory.getLogger(LeaderBoardImpl.class);

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private LeaderBoardWebSocketHandler leaderBoardWebSocketHandler;

    @Override
    public void assignRanksBasedOnPoints() {
        // Fetch all users
        List<User> users = userRepo.findAll();

        // Sort users by total points in descending order
        users.sort((u1, u2) -> Integer.compare(u2.getTotPoints(), u1.getTotPoints()));

        // Assign ranks based on sorted order
        int rank = 1;
        for (User user : users) {
            user.setUserRank(rank++);
        }

        userRepo.saveAll(users);
        logger.info("Ranks assigned successfully based on total points.");

        // Send notification to WebSocket clients after leaderboard update
        //notifyLeaderboardUpdate();
    }

    @Override
    public int getRankByUserId(Long userId) {
        User user = userRepo.findByUserId(userId); // Fetch user by ID
        if (user != null) {
            return user.getUserRank(); // Return user rank
        }
        throw new RuntimeException("User not found");
    }

    @Override
    public List<LeaderBoardDTO> getLeaderBoard() {
        List<User> users = userRepo.findAll();
        assignRanksBasedOnPoints();

        // Sort by rank and convert to LeaderboardDTO
        return users.stream()
                .sorted((u1, u2) -> Integer.compare(u1.getUserRank(), u2.getUserRank()))
                .map(user -> new LeaderBoardDTO(user.getUserName(), user.getTotPoints(), user.getUserRank())) // Map to LeaderboardDTO
                .collect(Collectors.toList());
    }

    public void notifyLeaderboardUpdate() {
        // You can customize the message format, e.g., convert the leaderboard to JSON or plain text
        String leaderboardUpdateMessage = "Leaderboard updated! Check out who's on rank 1";
        leaderBoardWebSocketHandler.sendLeaderboardUpdateNotification(leaderboardUpdateMessage);
    }
}
