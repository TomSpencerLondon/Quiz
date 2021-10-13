package com.example.quiz;

import java.util.ArrayList;
import java.util.List;

public class QuizSession {
  private final Quiz quiz;

  private List<Response> responses = new ArrayList<>();
  private int position = 0;

  public QuizSession(Quiz quiz) {
    this.quiz = quiz;
  }

  public Question question() {
    return quiz.questions().get(position++);
  }

  public void respondWith(String text) {
    responses.add(new Response(text, question()));
  }


  public boolean isLastAnswerCorrect() {
    int earlier = --this.position;
    Response response = responses.get(earlier);

    return response.isCorrect();
  }
}
