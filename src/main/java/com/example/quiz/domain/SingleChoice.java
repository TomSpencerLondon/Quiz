package com.example.quiz.domain;

import java.util.List;

public class SingleChoice {
  private Choice correct;
  private List<Choice> choices;

  public SingleChoice(Choice correct, List<Choice> choices) {
    this.correct = correct;
    this.choices = choices;
  }

  public List<Choice> choices() {
    return choices;
  }

  public boolean isCorrect(Choice choice) {
    return correct.equals(choice);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    SingleChoice that = (SingleChoice) o;

    if (!correct.equals(that.correct)) {
      return false;
    }
    return choices.equals(that.choices);
  }

  @Override
  public int hashCode() {
    int result = correct.hashCode();
    result = 31 * result + choices.hashCode();
    return result;
  }
}
