package com.example.quiz.domain;

import java.util.List;

public class Question {
  private SingleChoice singleChoice;
  private final String text;
  private MultipleChoice multipleChoice;
  private Long id;

  public Question(
      String text,
      SingleChoice singleChoice
  ) {
    this.text = text;
    this.singleChoice = singleChoice;
  }

  public Question(String text, MultipleChoice multipleChoice) {
    this.text = text;
    this.multipleChoice = multipleChoice;
  }

  public List<Choice> choices() {
    if (singleChoice == null) {
      return multipleChoice.choices();
    }
    return singleChoice.choices();
  }

  public boolean isCorrectAnswer(Choice... choices) {
    return singleChoice.isCorrect(choices[0]);
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String text() {
    return text;
  }

  public boolean isSingleChoice() {
    return singleChoice != null;
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append(this.text);

    this.singleChoice.choices().forEach((a) -> {
      sb.append("\n");
      sb.append(a);
    });

    return sb.toString();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    Question question = (Question) o;

    return id != null ? id.equals(question.id) : question.id == null;
  }

  @Override
  public int hashCode() {
    return id != null ? id.hashCode() : 0;
  }
}
