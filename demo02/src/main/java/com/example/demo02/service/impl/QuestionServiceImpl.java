package com.example.demo02.service.impl;

import com.example.demo02.model.Question;
import com.example.demo02.entity.DifficultyLevel;
import com.example.demo02.repository.QuestionRepository;
import com.example.demo02.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
public class QuestionServiceImpl implements QuestionService {

    @Autowired
    private QuestionRepository questionRepository;

    @Override
    public Question addQuestion(Question question) {
        return questionRepository.save(question);
    }

    @Override
    public List<Question> getAllQuestions() {
        return questionRepository.findAll();
    }

    @Override
    public Optional<Question> getQuestionById(Long id) {
        return questionRepository.findById(id);
    }

    @Override
    public Question updateQuestion(Long id, Question questionDetails) {
        return questionRepository.findById(id)
                .map(question -> {
                    question.setTitle(questionDetails.getTitle());
                    question.setDescription(questionDetails.getDescription());
                    question.setChoices(questionDetails.getChoices());
                    question.setCorrectAnswer(questionDetails.getCorrectAnswer());
                    question.setQuestionType(questionDetails.getQuestionType());
                    question.setDifficultyLevel(questionDetails.getDifficultyLevel());
                    return questionRepository.save(question);
                })
                .orElseThrow(() -> new RuntimeException("Question not found"));
    }

    @Override
    public void deleteQuestion(Long id) {
        questionRepository.deleteById(id);
    }

    @Override
    public List<Question> getQuestionsByDifficulty(DifficultyLevel difficultyLevel) {
        return questionRepository.findByDifficultyLevel(difficultyLevel);
    }

    @Override
    public List<Question> getRandomQuestions() {
        List<Question> allQuestions = questionRepository.findAll();
        return allQuestions.stream()
                .sorted((q1, q2) -> new Random().nextInt(2) - 1)
                .limit(10)
                .toList();
    }
}
