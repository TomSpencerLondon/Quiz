package com.example.quiz.domain;

import static org.apache.commons.lang3.builder.EqualsBuilder.reflectionEquals;
import static org.apache.commons.lang3.builder.HashCodeBuilder.reflectionHashCode;

import java.util.List;

public class Question {
  private final MultipleChoice choice;
  private final String text;
  private Long id;

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

  public List<Choice> answers() {
    return this.choice.answers();
  }

  public Choice correctAnswer() {
    return choice.correctAnswer();
  }

  public boolean isCorrectAnswer(Choice answer) {
    return answer.equals(correctAnswer());
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
