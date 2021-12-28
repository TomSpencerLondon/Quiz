package com.example.quiz.domain;

import static org.apache.commons.lang3.builder.EqualsBuilder.reflectionEquals;
import static org.apache.commons.lang3.builder.HashCodeBuilder.reflectionHashCode;

public class FinalMark {
  private int correct;
  private int incorrect;

  public FinalMark(int correct, int incorrect) {
    this.correct = correct;
    this.incorrect = incorrect;
  }

  public long correct() {
    return correct;
  }

  public long incorrect() {
    return incorrect;
  }

  @Override
  public boolean equals(Object other) {
    return reflectionEquals(this, other);
  }

  @Override
  public int hashCode() {
    return reflectionHashCode(this);
  }
}
