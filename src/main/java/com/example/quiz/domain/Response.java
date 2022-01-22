package com.example.quiz.domain;

public class Response {
  private final Choice choice;
  private final Question question;

  public Response(Question question, Choice choice) {
    this.choice = choice;
    this.question = question;
  }

  public boolean isCorrect() {
    return question.isCorrectAnswer(choice);
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

    if (!choice.equals(response.choice)) {
      return false;
    }
    return question.equals(response.question);
  }

  @Override
  public int hashCode() {
    int result = choice.hashCode();
    result = 31 * result + question.hashCode();
    return result;
  }
}
