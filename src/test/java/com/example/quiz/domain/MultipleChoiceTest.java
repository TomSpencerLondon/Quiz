package com.example.quiz.domain;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class MultipleChoiceTest {
    @Test
    void isCorrectReturnsFalseIfOnlyOneChoice() {
        // Given
        List<Choice> choices = List.of(
                new Choice("Answer 1", true),
                new Choice("Answer 2", true),
                new Choice("Answer 3", false));

        // When
        MultipleChoice multipleChoice = new MultipleChoice(choices);

        // Then
        boolean result = multipleChoice.isCorrect(new Choice("Answer 1", true));
        assertThat(result)
                .isFalse();
    }

    @Test
    void isCorrectReturnsTrueIfCalledWithAllCorrectChoices() {
        // Given
        List<Choice> choices = List.of(
                new Choice("Answer 1", true),
                new Choice("Answer 2", true),
                new Choice("Answer 3", false));

        // When
        MultipleChoice multipleChoice = new MultipleChoice(choices);

        // Then
        boolean result = multipleChoice.isCorrect(new Choice("Answer 1", true), new Choice("Answer 2", true));
        assertThat(result)
                .isTrue();
    }

    @Test
    void isCorrectReturnsFalseIfCorrectChoicesDontMatch() {
        // Given
        List<Choice> choices = List.of(
                new Choice("Answer 1", false),
                new Choice("Answer 2", true),
                new Choice("Answer 3", true));

        // When
        MultipleChoice multipleChoice = new MultipleChoice(choices);

        // Then
        boolean result = multipleChoice.isCorrect(new Choice("Answer 1", true),
                new Choice("Answer 2", true));
        assertThat(result)
                .isFalse();
    }
}