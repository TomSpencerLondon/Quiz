package com.example.quiz.domain;

import static org.apache.commons.lang3.builder.EqualsBuilder.reflectionEquals;
import static org.apache.commons.lang3.builder.HashCodeBuilder.reflectionHashCode;

public class FinalMark {
  private Long correct;
  private Long incorrect;

  public FinalMark(Long correct, Long incorrect) {
    this.correct = correct;
    this.incorrect = incorrect;
  }

  public Long correct() {
    return correct;
  }

  public Long incorrect() {
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
