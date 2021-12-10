package com.example.quiz.domain;

import java.util.List;

import static org.apache.commons.lang3.builder.EqualsBuilder.reflectionEquals;
import static org.apache.commons.lang3.builder.HashCodeBuilder.reflectionHashCode;

public class Question {
  private final MultipleChoice choice;
  private String text;
  private Integer id;

  public Question(
      String text,
      MultipleChoice choice
  ) {
    this.text = text;
    this.choice = choice;
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

    this.choice.answers().forEach((a) -> {
      sb.append("\n");
      sb.append(a);
    });

    return sb.toString();
  }

  public List<Answer> answers() {
    return this.choice.answers();
  }

  public Answer correctAnswer() {
    return choice.correctAnswer();
  }

  boolean isCorrectAnswer(String responseText) {
    return responseText.equals(correctAnswer().text());
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String text() {
    return text;
  }
}
