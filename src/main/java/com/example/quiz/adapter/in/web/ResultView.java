package com.example.quiz.adapter.in.web;

import com.example.quiz.domain.Grade;
import com.example.quiz.domain.Response;
import java.util.List;

public class ResultView {

  private final int percent;
  private final int correct;
  private final int incorrect;
  private final List<Response> responses;

  public ResultView(int percent, int correct, int incorrect,
      List<Response> responses) {
    this.percent = percent;
    this.correct = correct;
    this.incorrect = incorrect;
    this.responses = responses;
  }

  public static ResultView from(Grade grade) {
    final int correct = grade.correct();
    final int incorrect = grade.incorrect();
    final int percent = grade.percent();
    final List<Response> responses = grade.responses();
    return new ResultView(percent, correct, incorrect, responses);
  }

  public int getPercent() {
    return percent;
  }

  public int getCorrect() {
    return correct;
  }

  public int getIncorrect() {
    return incorrect;
  }

  public List<Response> getResponses() { return responses; }
}
