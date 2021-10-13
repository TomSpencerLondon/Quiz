package com.example.quiz;

public class QuizSession {
  private final Quiz quiz;

  public QuizSession(Quiz quiz) {
    this.quiz = quiz;
  }

  public Question question() {
    return quiz.questions().get(0);
  }
}
