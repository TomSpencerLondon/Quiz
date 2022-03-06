package com.example.quiz.domain;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class MultipleChoiceTest {
    @Test
    void isCorrectReturnsFalseIfOnlyOneChoice() {
        List<Choice> choices = List.of(new Choice("Answer 1", true), new Choice("Answer 2", false), new Choice("Answer 3", false));

        MultipleChoice multipleChoice = new MultipleChoice(choices);

        boolean result = multipleChoice.isCorrect(new Choice("Answer 1"));

        assertThat(result)
                .isFalse();
    }

    @Test
    void isCorrectReturnsTrueIfCalledWithAllCorrectChoices() {
        List<Choice> choices = List.of(new Choice("Answer 1", true), new Choice("Answer 2", true), new Choice("Answer 3", false));
        MultipleChoice multipleChoice = new MultipleChoice(choices);
        boolean result = multipleChoice.isCorrect(new Choice("Answer 1", true), new Choice("Answer 2", true));
        assertThat(result)
                .isTrue();
    }
}