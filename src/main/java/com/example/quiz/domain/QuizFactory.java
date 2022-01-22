package com.example.quiz.domain;

import com.example.quiz.application.port.QuestionRepository;

public class QuizFactory {

  private final QuestionRepository questionRepository;

  public QuizFactory(QuestionRepository questionRepository) {
    this.questionRepository = questionRepository;
  }

  public Quiz createQuiz() {
    return new Quiz(questionRepository);
  }
}
