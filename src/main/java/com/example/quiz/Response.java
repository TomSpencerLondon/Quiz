package com.example.quiz;

import static org.apache.commons.lang3.builder.EqualsBuilder.reflectionEquals;
import static org.apache.commons.lang3.builder.HashCodeBuilder.reflectionHashCode;

public class Response {
  private final String responseText;
  private final Question question;
  private final ResponseStatus status;

  public Response(String responseText, Question question) {
    this.responseText = responseText;
    this.question = question;
    this.status = statusFor(responseText, question);
  }

  private ResponseStatus statusFor(String responseText, Question question) {
    if (question.isCorrectAnswer(responseText)) {
      return ResponseStatus.CORRECT;
    } else {
      return ResponseStatus.INCORRECT;
    }
  }

  // TODO: delete this function - in favour
  //  of querying question status
  @Deprecated
  public boolean isCorrect() {
    Answer answer = this.question.correctAnswer();
    return this.responseText.equals(answer.text());
  }

  @Override
  public boolean equals(Object other) {
    return reflectionEquals(this, other);
  }

  @Override
  public int hashCode() {
    return reflectionHashCode(this);
  }

  public ResponseStatus status() {
    return this.status;
  }
}
