package com.example.quiz.domain;

import com.example.quiz.application.port.QuestionRepository;
import java.util.List;
import javax.transaction.Transactional;

public class Quiz {

  private final QuestionRepository questionRepository;

  public Quiz(QuestionRepository questionRepository) {
    this.questionRepository = questionRepository;
  }

  @Transactional
  public List<Question> questions() {
    return this.questionRepository.findAll();
  }

  public QuizSession start() {
    return new QuizSession(this);
  }
}
