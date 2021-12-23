package com.example.quiz.adapter.in.web;

import com.example.quiz.domain.Grade;

public class ResultView {

  private final int percent;
  private final int correct;
  private final int incorrect;

  public ResultView(int percent, int correct, int incorrect) {
    this.percent = percent;
    this.correct = correct;
    this.incorrect = incorrect;
  }

  public static ResultView from(Grade grade) {
    final int correct = (int) grade.finalMark().correct();
    final int incorrect = (int) grade.finalMark().incorrect();
    final int percent = grade.percent();
    return new ResultView(percent, correct, incorrect);
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
}
