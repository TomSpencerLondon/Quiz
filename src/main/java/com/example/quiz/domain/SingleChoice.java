package com.example.quiz.domain;

import static org.apache.commons.lang3.builder.EqualsBuilder.reflectionEquals;
import static org.apache.commons.lang3.builder.HashCodeBuilder.reflectionHashCode;

import java.util.List;

public class SingleChoice {
  private Choice correct;
  private List<Choice> choices;

  public SingleChoice(Choice correct, List<Choice> choices) {
    this.correct = correct;
    this.choices = choices;
  }

  @Override
  public boolean equals(Object other) {
    return reflectionEquals(this, other);
  }

  @Override
  public int hashCode() {
    return reflectionHashCode(this);
  }

  public Choice correctAnswer() {
    return this.correct;
  }

  public List<Choice> answers() {
    return this.choices;
  }
}
