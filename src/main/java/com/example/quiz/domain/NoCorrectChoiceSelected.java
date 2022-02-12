package com.example.quiz.domain;

public class NoCorrectChoiceSelected extends RuntimeException {

  public NoCorrectChoiceSelected(String message) {
    super(message);
  }
}
