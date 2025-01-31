package com.example.demo02.controller;


import com.example.demo02.dto.LeaderBoardDTO;
import com.example.demo02.dto.UserDTO;
import com.example.demo02.entity.User;
import com.example.demo02.model.Question;
import com.example.demo02.service.LeaderBoardService;
import com.example.demo02.service.QuestionService;
import com.example.demo02.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/leaderboard")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class LeaderBoardController {

    @Autowired
    private LeaderBoardService leaderBoardService;

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<List<LeaderBoardDTO>> getLeaderBoard() {
        List<LeaderBoardDTO> users = leaderBoardService.getLeaderBoard();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getRankByUserId(@PathVariable Long id) {
        Integer userRank = leaderBoardService.getRankByUserId(id);
        return ResponseEntity.ok(userRank);
    }

    @PutMapping("/update")
    public ResponseEntity<String> updateLeaderboard() {
        leaderBoardService.assignRanksBasedOnPoints();// Trigger leaderboard rank update
        leaderBoardService.notifyLeaderboardUpdate();
        return ResponseEntity.ok("Leaderboard updated!! Check out now!");
    }

}
