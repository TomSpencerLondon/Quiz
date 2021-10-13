package com.example.quiz;

import static org.apache.commons.lang3.builder.EqualsBuilder.reflectionEquals;
import static org.apache.commons.lang3.builder.HashCodeBuilder.reflectionHashCode;

public class Response {
  private final String text;
  private final Question question;

  public Response(String text, Question question) {
    this.text = text;
    this.question = question;
  }

  public boolean isCorrect() {
    Answer answer = this.question.correctAnswer();
    return this.text.equals(answer.text());
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
