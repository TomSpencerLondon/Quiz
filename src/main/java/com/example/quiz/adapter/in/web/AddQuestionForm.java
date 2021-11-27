package com.example.quiz.adapter.in.web;

import java.util.List;

public class AddQuestionForm {

  private final String text;
  private final String answer;
  private final String choice1;
  private final String choice2;
  private final String choice3;
  private final String choice4;

  public AddQuestionForm(String text, String answer, String choice1, String choice2,
      String choice3, String choice4) {
    this.text = text;
    this.answer = answer;
    this.choice1 = choice1;
    this.choice2 = choice2;
    this.choice3 = choice3;
    this.choice4 = choice4;
  }

  public String getText() {
    return text;
  }

  public String getAnswer() {
    return answer;
  }

  public String getChoice1() {
    return choice1;
  }

  public String getChoice2() {
    return choice2;
  }

  public String getChoice3() {
    return choice3;
  }

  public String getChoice4() {
    return choice4;
  }
}
