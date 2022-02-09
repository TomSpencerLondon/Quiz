package com.example.quiz.adapter.in.web;

public class AddQuestionForm {

  private String text;
  private ChoiceForm choice1;
  private ChoiceForm choice2;
  private ChoiceForm choice3;
  private ChoiceForm choice4;

  public AddQuestionForm() {
  }

  public AddQuestionForm(String text, ChoiceForm choice1, ChoiceForm choice2,
      ChoiceForm choice3, ChoiceForm choice4) {
    this.text = text;
    this.choice1 = choice1;
    this.choice2 = choice2;
    this.choice3 = choice3;
    this.choice4 = choice4;
  }

  public String getText() {
    return text;
  }

  public ChoiceForm getChoice1() {
    return choice1;
  }

  public ChoiceForm getChoice2() {
    return choice2;
  }

  public ChoiceForm getChoice3() {
    return choice3;
  }

  public ChoiceForm getChoice4() {
    return choice4;
  }

  public void setText(String text) {
    this.text = text;
  }

  public void setChoice1(ChoiceForm choice1) {
    this.choice1 = choice1;
  }

  public void setChoice2(ChoiceForm choice2) {
    this.choice2 = choice2;
  }

  public void setChoice3(ChoiceForm choice3) {
    this.choice3 = choice3;
  }

  public void setChoice4(ChoiceForm choice4) {
    this.choice4 = choice4;
  }

}
