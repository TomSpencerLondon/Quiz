package com.example.quiz.domain;

import static org.apache.commons.lang3.builder.EqualsBuilder.reflectionEquals;
import static org.apache.commons.lang3.builder.HashCodeBuilder.reflectionHashCode;

public class FinalMark {
  private long correct;
  private long incorrect;

  public FinalMark(long correct, long incorrect) {
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
