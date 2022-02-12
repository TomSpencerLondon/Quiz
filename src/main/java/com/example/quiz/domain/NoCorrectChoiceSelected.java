package com.example.quiz.domain;

import com.example.quiz.adapter.in.web.ChoiceForm;

public class NoCorrectChoiceSelected extends RuntimeException {

  public NoCorrectChoiceSelected(String message) {
    super(message);
  }

  public NoCorrectChoiceSelected(ChoiceForm... choices) {
    this(String.format("No choices (%s,%s,%s,%s) are marked as correct", choices));
  }
}
