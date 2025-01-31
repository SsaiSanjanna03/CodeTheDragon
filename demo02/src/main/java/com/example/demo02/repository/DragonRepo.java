package com.example.demo02.repository;

import com.example.demo02.entity.Dragon;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface DragonRepo extends JpaRepository<Dragon, Long> {
    List<Dragon> findByUser_UserId(Long userId);
}
