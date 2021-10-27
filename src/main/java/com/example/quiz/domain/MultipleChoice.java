package com.example.quiz.domain;

import com.example.quiz.domain.Answer;
import java.util.List;

import static org.apache.commons.lang3.builder.EqualsBuilder.reflectionEquals;
import static org.apache.commons.lang3.builder.HashCodeBuilder.reflectionHashCode;

public class MultipleChoice {
  private Answer correct;
  private List<Answer> answers;

  public MultipleChoice(Answer correct, List<Answer> answers) {
    this.correct = correct;
    this.answers = answers;
  }

  @Override
  public boolean equals(Object other) {
    return reflectionEquals(this, other);
  }

  @Override
  public int hashCode() {
    return reflectionHashCode(this);
  }

  public Answer correctAnswer() {
    return this.correct;
  }

  public List<Answer> answers() {
    return this.answers;
  }
}
