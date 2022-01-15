package com.example.quiz.domain;

import java.util.List;

public class Question {
  private final SingleChoice singleChoice;
  private final String text;
  private Long id;

  public Question(
      String text,
      SingleChoice singleChoice
  ) {
    this.text = text;
    this.singleChoice = singleChoice;
  }

  public List<Choice> choices() {
    return this.singleChoice.answers();
  }

  public boolean isCorrectAnswer(Choice choice) {
    return singleChoice.isCorrect(choice);
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String text() {
    return text;
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append(this.text);

    this.singleChoice.answers().forEach((a) -> {
      sb.append("\n");
      sb.append(a);
    });

    return sb.toString();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    Question question = (Question) o;

    return id != null ? id.equals(question.id) : question.id == null;
  }

  @Override
  public int hashCode() {
    return id != null ? id.hashCode() : 0;
  }
}
