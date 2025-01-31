package com.example.demo02.service;

import com.example.demo02.model.Question;
import com.example.demo02.entity.DifficultyLevel;

import java.util.List;
import java.util.Optional;

public interface QuestionService {
    Question addQuestion(Question question);

    List<Question> getAllQuestions();

    Optional<Question> getQuestionById(Long id);

    Question updateQuestion(Long id, Question questionDetails);

    void deleteQuestion(Long id);

    List<Question> getQuestionsByDifficulty(DifficultyLevel difficultyLevel);

    List<Question> getRandomQuestions();
}
