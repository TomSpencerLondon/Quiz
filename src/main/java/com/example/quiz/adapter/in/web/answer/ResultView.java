package com.example.quiz.adapter.in.web.answer;

import com.example.quiz.domain.Grade;

import java.util.List;

public class ResultView {

  private final int percent;
  private final int correct;
  private final int incorrect;
  private final List<ResponseView> responsesViews;

  public ResultView(int percent, int correct, int incorrect,
      List<ResponseView> responsesViews) {
    this.percent = percent;
    this.correct = correct;
    this.incorrect = incorrect;
    this.responsesViews = responsesViews;
  }

  public static ResultView from(Grade grade) {
    final int correct = grade.correct();
    final int incorrect = grade.incorrect();
    final int percent = grade.percent();
    final List<ResponseView> responseViews = grade.responses()
        .stream()
        .map(ResponseView::from).toList();
    return new ResultView(percent, correct, incorrect, responseViews);
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

  public List<ResponseView> getResponsesViews() { return responsesViews; }
}
