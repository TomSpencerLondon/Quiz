package com.example.quiz.hexagon.domain.domain;

import com.example.quiz.hexagon.domain.Choice;
import com.example.quiz.hexagon.domain.MultipleChoice;
import com.example.quiz.hexagon.domain.Question;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class MultipleChoiceTest {
    @Test
    void questionWithMultipleChoiceIsNotSingleChoice() {
        // Given
        final Question question = new QuestionBuilder()
                .withDefaultMultipleChoice()
                .build();

        // Then
        assertThat(question.isSingleChoice())
                .isFalse();
    }

    @Test
    void isCorrectReturnsFalseIfOnlyOneChoice() {
        // Given
        List<Choice> choices = new ChoiceBuilder()
                .withCorrectChoice()
                .withCorrectChoice()
                .withIncorrectChoice()
                .asList();

        // When
        MultipleChoice multipleChoice = new MultipleChoice(choices);

        // Then
        Choice[] incompleteAnswer = new ChoiceBuilder()
                .withCorrectChoice()
                .asArray();
        assertThat(multipleChoice.isCorrect(incompleteAnswer))
                .isFalse();
    }

    @Test
    void multipleChoiceQuestionWithAllCorrectAnswersIsCorrectAnswer() {
        // Given
        List<Choice> choices = new ChoiceBuilder()
                .withCorrectChoice()
                .withCorrectChoice()
                .withIncorrectChoice()
                .asList();

        final Question question = new QuestionBuilder()
                .withMultipleChoice(choices)
                .build();

        // Then
        Choice[] correctChoices = new ChoiceBuilder()
                .withCorrectChoice()
                .withCorrectChoice()
                .asArray();
        assertThat(question.isCorrectAnswer(correctChoices))
                .isTrue();
    }

    @Test
    void isInCorrectIfCorrectChoicesDontMatch() {
        // Given
        List<Choice> choices = new ChoiceBuilder()
                .withIncorrectChoice()
                .withCorrectChoice()
                .withCorrectChoice()
                .asList();

        // When
        MultipleChoice multipleChoice = new MultipleChoice(choices);

        // Then
        Choice[] wrongAnswer = new ChoiceBuilder()
                .withCorrectChoice()
                .withCorrectChoice()
                .asArray();
        assertThat(multipleChoice.isCorrect(wrongAnswer))
                .isFalse();
    }
}