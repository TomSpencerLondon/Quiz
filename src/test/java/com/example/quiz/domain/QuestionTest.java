package com.example.quiz.domain;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class QuestionTest {

    @Test
    void questionWithSingleChoiceIsSingleChoice() {
        // Given
        final Question question = new Question("Question 1",
                new SingleChoice(List.of(new Choice("Answer 1", true))));

        // Then
        assertThat(question.isSingleChoice())
                .isTrue();
    }

    @Test
    void questionWithMultipleChoiceIsMultipleChoice() {
        // Given
        final Question question = new Question("Question 1",
                new MultipleChoice(List.of(new Choice("Answer 1", true), new Choice("Answer 2", true), new Choice("Answer 3", false))));

        // Then
        assertThat(question.isSingleChoice())
                .isFalse();
    }

    @Test
    void multipleChoiceQuestionWithAllCorrectAnswersReturnsTrue() {
        // Given
        final Question question = new Question("Question 1",
                new MultipleChoice(List.of(new Choice("Answer 1", true), new Choice("Answer 2", true), new Choice("Answer 3", false))));

        // Then
        boolean correctAnswer = question.isCorrectAnswer(new Choice("Answer 1", true), new Choice("Answer 2", true));
        assertThat(correctAnswer)
                .isTrue();
    }

    @Test
    void singleChoiceWithCorrectAnswerReturnsTrue() {
        // Given
        final Question question = new Question("Question 1",
                new SingleChoice(List.of(new Choice("Answer 1", true), new Choice("Answer 2", false), new Choice("Answer 3", false))));

        // Then
        boolean correctAnswer = question.isCorrectAnswer(new Choice("Answer 1", true));
        assertThat(correctAnswer).isTrue();
    }
}