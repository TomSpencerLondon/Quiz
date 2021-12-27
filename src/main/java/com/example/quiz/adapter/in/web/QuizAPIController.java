package com.example.quiz.adapter.in.web;

import com.example.quiz.application.QuestionService;
import com.example.quiz.domain.Question;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class QuizAPIController {
  private final QuestionService questionService;

  @Autowired
  public QuizAPIController(QuestionService questionService) {
    this.questionService = questionService;
  }

  @GetMapping("/api/questions")
  public List<QuestionView> getAllQuestions() {
    final List<Question> questions = questionService.findAll();

    final List<QuestionView> questionViews = questions.stream()
        .map(QuestionView::of)
        .toList();

    return questionViews;
  }

  @PostMapping("/api/questions")
  public ResponseEntity<Object> createQuestion(AddQuestionForm addQuestionForm) {
    System.out.println(addQuestionForm);
    return ResponseEntity.status(201).build();
  }
}
