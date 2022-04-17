package com.example.quiz.domain;

import com.example.quiz.domain.factories.SingleChoiceQuestionTestFactory;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ResponseTest {

    @Test
    void correctResponseIsMarkedAsCorrect() {
        // Given
        Question singleChoiceQuestion = SingleChoiceQuestionTestFactory.createSingleChoiceQuestion();
        Choice choice = new Choice("Answer 1", true);

        // When
        Response response = new Response(singleChoiceQuestion.getId(), singleChoiceQuestion.isCorrectAnswer(choice), choice);

        // Then
        assertThat(response.isCorrect())
                .isTrue();
    }

    @Test
    void wrongResponseIsMarkedAsIncorrect() {
        // Given
        Question singleChoiceQuestion = SingleChoiceQuestionTestFactory.createSingleChoiceQuestion();
        Choice choice = new Choice("Answer 2");

        // When
        Response response = new Response(singleChoiceQuestion.getId(), singleChoiceQuestion.isCorrectAnswer(choice), choice);

        // Then
        assertThat(response.isCorrect())
                .isFalse();
    }
}