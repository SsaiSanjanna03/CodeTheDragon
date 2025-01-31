package com.example.demo02.service;

import com.example.demo02.dto.LeaderBoardDTO;
import com.example.demo02.dto.UserDTO;
import com.example.demo02.entity.User;

import java.util.List;

public interface LeaderBoardService {
    void assignRanksBasedOnPoints();
    int getRankByUserId(Long userId);
    List<LeaderBoardDTO> getLeaderBoard();
    void notifyLeaderboardUpdate();

}
