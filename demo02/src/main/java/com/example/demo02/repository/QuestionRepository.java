package com.example.demo02.repository;

import com.example.demo02.entity.DifficultyLevel;
import com.example.demo02.model.Question;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuestionRepository extends JpaRepository<Question, Long> {
    List<Question> findByDifficultyLevel(DifficultyLevel difficultyLevel);
}
