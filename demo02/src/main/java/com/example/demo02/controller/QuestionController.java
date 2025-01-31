package com.example.demo02.controller;

import com.example.demo02.model.Question;
import com.example.demo02.entity.User;
import com.example.demo02.service.QuestionService;
import com.example.demo02.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/questions")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class QuestionController {

   @Autowired
   private QuestionService questionService;

   @Autowired
   private UserService userService;

   @PostMapping
   public ResponseEntity<Question> addQuestion(@RequestBody Question question) {
      Question createdQuestion = questionService.addQuestion(question);
      return ResponseEntity.status(HttpStatus.CREATED).body(createdQuestion);
   }

   @GetMapping
   public ResponseEntity<List<Question>> getAllQuestions() {
      List<Question> questions = questionService.getAllQuestions();
      return ResponseEntity.ok(questions);
   }

   @GetMapping("/{question_id}")
   public ResponseEntity<Question> getQuestionById(@PathVariable Long question_id) {
      Optional<Question> question = questionService.getQuestionById(question_id);
      return question.map(ResponseEntity::ok)
             .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
   }

   @PutMapping("/{question_id}")
   public ResponseEntity<Question> updateQuestion(@PathVariable Long question_id, @RequestBody Question questionDetails) {
      Question updatedQuestion = questionService.updateQuestion(question_id, questionDetails);
      return ResponseEntity.ok(updatedQuestion);
   }

   @DeleteMapping("/{question_id}")
   public ResponseEntity<String> deleteQuestion(@PathVariable Long question_id) {
      questionService.deleteQuestion(question_id);
      return ResponseEntity.ok("Question deleted successfully");
   }

   @GetMapping("/user-questions/{userId}")
   public ResponseEntity<List<Question>> getQuestionsForUser(@PathVariable Long userId) {
      User user = userService.getUserById(userId);
      if (user == null) {
         return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
      }

      List<Question> questions = user.getDifficulty_level() != null
             ? questionService.getQuestionsByDifficulty(user.getDifficulty_level())
             : questionService.getRandomQuestions();

      return ResponseEntity.ok(questions);
   }

   @GetMapping("/multiplayer-questions")
   public ResponseEntity<List<Question>> getMultiplayerQuestions() {
      List<Question> questions = questionService.getRandomQuestions();
      return ResponseEntity.ok(questions);
   }
}
