package com.example.quiz.adapter.in.web;

public class ChoiceForm {
  private String choice;
  private boolean correctAnswer;

  public ChoiceForm() {
  }

  public ChoiceForm(String choice, boolean correctAnswer) {
    this.choice = choice;
    this.correctAnswer = correctAnswer;
  }

  public String getChoice() {
    return choice;
  }

  public void setChoice(String choice) {
    this.choice = choice;
  }

  public boolean isCorrectAnswer() {
    return correctAnswer;
  }

  public void setCorrectAnswer(boolean correctAnswer) {
    this.correctAnswer = correctAnswer;
  }
}
