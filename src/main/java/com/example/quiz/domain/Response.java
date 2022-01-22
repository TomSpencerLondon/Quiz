package com.example.quiz.domain;

public class Response {
  private final ResponseStatus status;
  private Choice choice;
  private final Question question;

  public Response(Choice choice, Question question) {
    this.choice = choice;
    this.question = question;
    this.status = statusFor(choice);
  }

  public ResponseStatus status() {
    return status;
  }

  private ResponseStatus statusFor(Choice choice) {
    if (question.isCorrectAnswer(choice)) {
      return ResponseStatus.CORRECT;
    } else {
      return ResponseStatus.INCORRECT;
    }
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    Response response = (Response) o;

    if (status != response.status) {
      return false;
    }
    return question.equals(response.question);
  }

  @Override
  public int hashCode() {
    int result = status.hashCode();
    result = 31 * result + question.hashCode();
    return result;
  }
}
