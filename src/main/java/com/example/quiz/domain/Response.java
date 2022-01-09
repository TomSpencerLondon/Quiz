package com.example.quiz.domain;

import static org.apache.commons.lang3.builder.EqualsBuilder.reflectionEquals;
import static org.apache.commons.lang3.builder.HashCodeBuilder.reflectionHashCode;

public class Response {
  private final ResponseStatus status;
  private final Question question;

  public Response(Choice responseText, Question question) {
    this.question = question;
    this.status = statusFor(responseText);
  }

  public ResponseStatus status() {
    return this.status;
  }

  private ResponseStatus statusFor(Choice choice) {
    if (question.isCorrectAnswer(choice)) {
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
