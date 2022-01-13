package com.example.quiz.domain;

import static org.apache.commons.lang3.builder.EqualsBuilder.reflectionEquals;
import static org.apache.commons.lang3.builder.HashCodeBuilder.reflectionHashCode;

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

  @Override
  public boolean equals(Object other) {
    return reflectionEquals(this, other);
  }

  @Override
  public int hashCode() {
    return reflectionHashCode(this);
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
}
