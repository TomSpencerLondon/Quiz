package com.example.quiz.domain;

import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class CorrectAnswerTest {

  @Test
  void knowsOneAnswer() {
    // Given
    Question question = new Question("Question 1",
        new SingleChoice(
            new Choice("Answer 1"),
            Collections.singletonList(
                new Choice("Answer 1")
            )));

    // When
    List<Choice> choices = question.choices();

    // Then
    assertThat(choices).containsExactly(new Choice("Answer 1"));
  }

  @Test
  void knowsSeveralAnswers() {
    List<Choice> choices = List.of(new Choice("Answer 1"),
        new Choice("Answer 2"),
        new Choice("Answer 3"),
        new Choice("Answer 4"));
    // Given
    Question question = new Question(
        "Question 1",
        new SingleChoice(
            new Choice("Answer 1"),
            choices)
    );

    // When
    List<Choice> result = question.choices();

    // Then
    assertThat(result)
        .containsExactly(
            new Choice("Answer 1"),
            new Choice("Answer 2"),
            new Choice("Answer 3"),
            new Choice("Answer 4")
        );
  }

  @Test
  void knowsCorrectAnswer() {
    // Given
    final Choice correct = new Choice("Answer 3");
    Question question = new Question(
        "Question 1",
        new SingleChoice(
            correct,
            List.of(new Choice("Answer 2"), new Choice("Answer 3"))
        ));

    // When
    final boolean correctAnswer = question.isCorrectAnswer(correct);

    // Then
    assertThat(correctAnswer)
        .isTrue();
  }

  @Test
  void correctChoiceMustBeOneOfTheChoices() {
    // Given
    Choice not_a_choice = new Choice("Not a choice");
    List<Choice> choices = List.of(
        new Choice("Answer 1"),
        new Choice("Answer 2"),
        new Choice("Answer 3"),
        new Choice("Answer 4"));

    // When + Then
    assertThatThrownBy(() -> {
      new SingleChoice(not_a_choice, choices);
    }).isInstanceOf(IllegalArgumentException.class)
        .hasMessage(String.format(
            "'%s' is not among %s",
            not_a_choice.text(),
            choices
        ));
  }
}
