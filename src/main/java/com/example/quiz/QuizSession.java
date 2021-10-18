package com.example.quiz;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class QuizSession {
  private final Quiz quiz;
  private final Iterator<Question> iterator;

  private List<Response> responses = new ArrayList<>();
  private Response lastResponse;

  public QuizSession(Quiz quiz) {
    this.quiz = quiz;
    iterator = quiz.questions().iterator();
  }

  public Question question() {
    return iterator.next();
  }

  public QuestionStatus respondWith(String text, Question question) {
    lastResponse = new Response(text, question);
    responses.add(lastResponse);
    return lastResponse.status();
  }

  public boolean isLastResponseCorrect() {
    return this.lastResponse.isCorrect();
  }

  public boolean isFinished() {
    return !iterator.hasNext();
  }
}
