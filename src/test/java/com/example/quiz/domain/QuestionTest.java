package com.example.quiz.domain;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class QuestionTest {

    @Test
    void questionWithSingleChoiceIsSingleChoice() {
        // Given
        final Question question = new QuestionBuilder().withDefaultSingleChoice().build();

        // Then
        assertThat(question.isSingleChoice())
                .isTrue();
    }

    @Test
    void singleChoiceWithCorrectAnswerReturnsTrue() {
        // Given
        final Question question = new QuestionBuilder()
                .withDefaultSingleChoice()
                .build();

        // Then
        Choice[] correctChoice = new ChoiceBuilder()
                .withCorrectChoice()
                .asArray();
        boolean correctAnswer = question.isCorrectAnswer(correctChoice);
        assertThat(correctAnswer).isTrue();
    }
}