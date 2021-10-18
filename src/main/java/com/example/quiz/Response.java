package com.example.quiz;

import static org.apache.commons.lang3.builder.EqualsBuilder.reflectionEquals;
import static org.apache.commons.lang3.builder.HashCodeBuilder.reflectionHashCode;

public class Response {
  private final String responseText;
  private final Question question;
  private final QuestionStatus status;

  public Response(String responseText, Question question) {
    this.responseText = responseText;
    this.question = question;
    if (question.isCorrectAnswer(responseText)) {
      this.status = QuestionStatus.CORRECT;
    } else {
      this.status = QuestionStatus.INCORRECT;
    }
  }

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

  public QuestionStatus status() {
    return this.status;
  }
}
