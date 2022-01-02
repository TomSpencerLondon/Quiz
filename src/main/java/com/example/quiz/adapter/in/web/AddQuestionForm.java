package com.example.quiz.adapter.in.web;

import java.util.ArrayList;
import java.util.List;

public class AddQuestionForm {

  private String text;
  private String answer;
  private String choice1;
  private String choice2;
  private String choice3;
  private String choice4;
  private List<String> choices = new ArrayList<>();

  public AddQuestionForm() {
  }

  public AddQuestionForm(String text, String choice1, String choice2,
      String choice3, String choice4) {
    this.text = text;
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

  public void setText(String text) {
    this.text = text;
  }

  public void setAnswer(String answer) {
    this.answer = answer;
  }

  public void setChoice1(String choice1) {
    this.choices.add(choice1);
    this.choice1 = choice1;
  }

  public void setChoice2(String choice2) {
    this.choices.add(choice2);
    this.choice2 = choice2;
  }

  public void setChoice3(String choice3) {
    this.choices.add(choice3);
    this.choice3 = choice3;
  }

  public void setChoice4(String choice4) {
    this.choices.add(choice4);
    this.choice4 = choice4;
  }

  public List<String> getChoices() {
    return this.choices;
  }
}
