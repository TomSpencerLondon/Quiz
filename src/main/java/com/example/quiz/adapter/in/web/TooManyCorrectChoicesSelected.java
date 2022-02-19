package com.example.quiz.adapter.in.web;

public class TooManyCorrectChoicesSelected extends RuntimeException {

  public TooManyCorrectChoicesSelected(String message) {
    super(message);
  }

  public TooManyCorrectChoicesSelected(ChoiceForm... choices) {
    this(String.format("Too many choices (%s,%s,%s,%s) are marked as correct", choices));
  }
}