package com.example.quiz.domain;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class MultipleChoiceTest {
    @Test
    void isCorrectReturnsFalseIfOnlyOneChoice() {
        List<Choice> correctChoices = List.of(new Choice("Answer 1"), new Choice("Answer 2"));

        MultipleChoice multipleChoice = new MultipleChoice(correctChoices, List.of(new Choice("Answer 1")));

        boolean result = multipleChoice.isCorrect(new Choice("Answer 1"));

        assertThat(result)
                .isFalse();
    }

    @Test
    void isCorrectReturnsTrueIfCalledWithAllCorrectChoices() {
        List<Choice> correctChoices = List.of(new Choice("Answer 1"), new Choice("Answer 2"));

        MultipleChoice multipleChoice = new MultipleChoice(correctChoices, List.of(new Choice("Answer 1")));

        boolean result = multipleChoice.isCorrect(new Choice("Answer 1"), new Choice("Answer 2"));

        assertThat(result)
                .isTrue();
    }
}