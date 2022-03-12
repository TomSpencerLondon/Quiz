package com.example.quiz.domain;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class SingleChoiceTest {
    @Test
    void isCorrectReturnsTrueIfCorrectChoice() {
        List<Choice> choices = List.of(
                new Choice("Answer 1", true),
                new Choice("Answer 2", false),
                new Choice("Answer 3", false));

        SingleChoice singleChoice = new SingleChoice(choices);

        boolean result = singleChoice.isCorrect(new Choice("Answer 1", true));

        assertThat(result)
                .isTrue();
    }

    @Test
    void isCorrectReturnsFalsIfIncorrectChoice() {
        List<Choice> choices = List.of(
                new Choice("Answer 1", true),
                new Choice("Answer 2", false),
                new Choice("Answer 3", false));

        SingleChoice singleChoice = new SingleChoice(choices);

        boolean result = singleChoice.isCorrect(new Choice("Answer 2", true));

        assertThat(result)
                .isFalse();
    }
}