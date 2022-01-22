package com.example.quiz.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class ResponseTest {

  @Test
  void correctResponseIsMarkedAsCorrect() {
    Question singleChoiceQuestion = SingleChoiceQuestionTestFactory.createSingleChoiceQuestion();

    Choice answer = new Choice("Answer 1");

    Response response = new Response(singleChoiceQuestion, answer);

    assertThat(response.isCorrect())
        .isTrue();
  }

  @Test
  void wrongResponseIsMarkedAsIncorrect() {
    Question singleChoiceQuestion = SingleChoiceQuestionTestFactory.createSingleChoiceQuestion();

    Choice answer = new Choice("Answer 2");

    Response response = new Response(singleChoiceQuestion, answer);

    assertThat(response.isCorrect())
        .isFalse();
  }
}