package com.example.quiz.adapter.in.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class QuizAPIController {

  @GetMapping("/api/questions")
  public Object getAllQuestions() {
    return null;
  }
}
