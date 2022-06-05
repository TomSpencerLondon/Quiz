package com.example.quiz.domain;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class SingleChoiceTest {
    @Test
    void questionWithSingleChoiceIsSingleChoice() {
        // Given
        final Question question = new QuestionBuilder().withDefaultSingleChoice().build();

        // Then
        assertThat(question.isSingleChoice())
                .isTrue();
    }

    @Test
    void singleChoiceQuestionWithCorrectAnswerReturnsTrue() {
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

    @Test
    void isCorrectReturnsTrueIfCorrectChoice() {
        // Given
        List<Choice> choices = new ChoiceBuilder()
                .withCorrectChoice()
                .withIncorrectChoice()
                .withIncorrectChoice().asList();
        SingleChoice singleChoice = new SingleChoice(choices);

        // Then
        boolean result = singleChoice.isCorrect(new Choice("Answer 1", true));
        assertThat(result)
                .isTrue();
    }

    @Test
    void isCorrectReturnsFalseIfIncorrectChoice() {
        // Given
        List<Choice> choices = new ChoiceBuilder()
                .withCorrectChoice()
                .withIncorrectChoice()
                .withIncorrectChoice().asList();
        ChoiceType singleChoice = new SingleChoice(choices);

        // Then
        boolean result = singleChoice.isCorrect(new Choice("Answer 2", true));
        assertThat(result)
                .isFalse();
    }

    @Test
    void isCorrectThrowsExceptionIfMoreThanOneChoice() {
        // Given
        List<Choice> choices = new ChoiceBuilder()
                .withCorrectChoice()
                .withIncorrectChoice()
                .withIncorrectChoice().asList();
        ChoiceType singleChoice = new SingleChoice(choices);

        // Then
        assertThatThrownBy(() -> singleChoice.isCorrect(new Choice("Answer 1", true), new Choice("Answer 2", true)))
                .isInstanceOf(TooManyCorrectChoicesSelected.class);
    }
}