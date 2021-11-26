package com.example.quiz.adapter.in.web;

import java.util.List;

public class QuestionRequest {

  private final String text;
  private final String answer;
  private final List<String> choices;

  public QuestionRequest(String text, String answer, List<String> choices) {
    this.text = text;
    this.answer = answer;
    this.choices = choices;
  }

  public String getText() {
    return text;
  }

  public String getAnswer() {
    return answer;
  }

  public List<String> getChoices() {
    return choices;
  }


}
