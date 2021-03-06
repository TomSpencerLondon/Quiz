package com.tomspencerlondon.quiz.hexagon.domain.domain;

import com.tomspencerlondon.quiz.hexagon.domain.Choice;
import com.tomspencerlondon.quiz.hexagon.domain.Question;
import com.tomspencerlondon.quiz.hexagon.domain.QuestionId;
import com.tomspencerlondon.quiz.hexagon.domain.Response;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ResponseTest {

    @Test
    void correctResponseIsMarkedAsCorrect() {
        // Given
        Question singleChoiceQuestion = new QuestionBuilder()
                .withQuestionId(1L)
                .withDefaultSingleChoice()
                .build();
        Choice choice = new ChoiceBuilder().withCorrectChoice().build();

        // When
        Response response = new Response(QuestionId.of(1L), singleChoiceQuestion.isCorrectAnswer(choice), choice);

        // Then
        assertThat(response.isCorrect())
                .isTrue();
    }

    @Test
    void wrongResponseIsMarkedAsIncorrect() {
        // Given
        Question singleChoiceQuestion = new QuestionBuilder()
                .withQuestionId(1L)
                .withDefaultSingleChoice()
                .build();
        Choice choice = new ChoiceBuilder().withIncorrectChoice().build();

        // When
        Response response = new Response(singleChoiceQuestion.getId(), singleChoiceQuestion.isCorrectAnswer(choice), choice);

        // Then
        assertThat(response.isCorrect())
                .isFalse();
    }
}