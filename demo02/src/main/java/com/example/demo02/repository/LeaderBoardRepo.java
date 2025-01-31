package com.example.demo02.repository;

import com.example.demo02.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LeaderBoardRepo extends JpaRepository<User, Long>{
    // new method to retrieve only top 10 users
    List<User> findTop10ByOrderByUserRankAsc();
}
