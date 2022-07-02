package com.tomspencerlondon.quiz.hexagon.domain.domain;

import com.tomspencerlondon.quiz.hexagon.domain.*;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class SingleChoiceTest {
    @Test
    void questionWithSingleChoiceIsSingleChoice() {
        // Given
        Question question = new QuestionBuilder()
                .withDefaultSingleChoice()
                .build();

        // Then
        assertThat(question.isSingleChoice())
                .isTrue();
    }

    @Test
    void singleChoiceQuestionWithCorrectAnswerReturnsTrue() {
        // Given
        Question question = new QuestionBuilder()
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
        ChoiceBuilder choiceBuilder = new ChoiceBuilder();
        List<Choice> choices = choiceBuilder
                .withCorrectChoice()
                .withIncorrectChoice()
                .withIncorrectChoice()
                .asList();
        SingleChoice singleChoice = new SingleChoice(choices);

        // Then
        boolean result = singleChoice.isCorrect(choiceBuilder.anyCorrectChoice());
        assertThat(result)
                .isTrue();
    }

    @Test
    void isCorrectReturnsFalseIfIncorrectChoice() {
        // Given
        ChoiceBuilder choiceBuilder = new ChoiceBuilder();
        List<Choice> choices = choiceBuilder
                .withCorrectChoice()
                .withIncorrectChoice()
                .withIncorrectChoice()
                .asList();
        ChoiceType singleChoice = new SingleChoice(choices);

        // Then
        boolean result = singleChoice.isCorrect(choiceBuilder.anyIncorrectChoice());
        assertThat(result)
                .isFalse();
    }

    @Test
    void isCorrectThrowsExceptionIfMoreThanOneChoice() {
        // Given
        List<Choice> choices = new ChoiceBuilder()
                .withCorrectChoice()
                .withIncorrectChoice()
                .withIncorrectChoice()
                .asList();
        ChoiceType singleChoice = new SingleChoice(choices);

        // Then
        assertThatThrownBy(() -> singleChoice.isCorrect(choices.get(0), choices.get(1)))
                .isInstanceOf(TooManyCorrectChoicesSelected.class);
    }
}