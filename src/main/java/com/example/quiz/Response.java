package com.example.quiz;

import static org.apache.commons.lang3.builder.EqualsBuilder.reflectionEquals;
import static org.apache.commons.lang3.builder.HashCodeBuilder.reflectionHashCode;

public class Response {
  private final ResponseStatus status;

  public Response(String responseText, Question question) {
    this.status = statusFor(responseText, question);
  }

  public ResponseStatus status() {
    return this.status;
  }

  private ResponseStatus statusFor(String responseText, Question question) {
    if (question.isCorrectAnswer(responseText)) {
      return ResponseStatus.CORRECT;
    } else {
      return ResponseStatus.INCORRECT;
    }
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
