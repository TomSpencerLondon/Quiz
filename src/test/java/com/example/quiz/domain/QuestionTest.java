package com.example.quiz.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.Test;

class QuestionTest {

  @Test
  void questionWithSingleChoiceIsSingleChoice() {
    final Question question = new Question("Question 1",
        new SingleChoice(new Choice("Answer 1"), List.of(new Choice("Answer 1"))));

    assertThat(question.isSingleChoice())
        .isTrue();
  }

  @Test
  void questionWithMultipleChoiceIsMultipleChoice() {
    final Question question = new Question("Question 1",
        new MultipleChoice(List.of(new Choice("Answer 1"))));

    assertThat(question.isSingleChoice())
        .isFalse();
  }
}