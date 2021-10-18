package com.example.quiz;

import java.util.ArrayList;
import java.util.List;

public class QuizSession {
  private final Quiz quiz;

  private List<Response> responses = new ArrayList<>();
  private int position = 0;
  private Response lastResponse;

  public QuizSession(Quiz quiz) {
    this.quiz = quiz;
  }

  public Question question() {
    return quiz.questions().get(position++);
  }

  public QuestionStatus respondWith(String text, Question question) {
    lastResponse = new Response(text, question);
    responses.add(lastResponse);
    return lastResponse.status();
  }

  public boolean isLastAnswerCorrect() {
    return this.lastResponse.isCorrect();
  }

  public boolean isFinished() {
    return this.position == quiz.questions().size();
  }
}
