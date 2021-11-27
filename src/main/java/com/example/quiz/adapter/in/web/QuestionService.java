package com.example.quiz.adapter.in.web;

import com.example.quiz.domain.Question;
import com.example.quiz.domain.port.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class QuestionService {
  private QuestionRepository questionRepository;

  @Autowired
  public QuestionService(QuestionRepository questionRepository) {
    this.questionRepository = questionRepository;
  }

  public Question add(Question question) {
    return questionRepository.save(question);
  }
}
