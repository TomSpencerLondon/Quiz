package com.example.quiz.domain;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class QuestionTest {

    @Test
    void questionWithSingleChoiceIsSingleChoice() {
        final Question question = new Question("Question 1",
                new SingleChoice(new Choice("Answer 1"), List.of(new Choice("Answer 1"))));

        assertThat(question.isSingleChoice())
                .isTrue();
    }

    @Test
    void questionWithMultipleChoiceIsMultipleChoice() {
        List<Choice> correctChoices = List.of(new Choice("Answer 1"), new Choice("Answer 2"));

        final Question question = new Question("Question 1",
                new MultipleChoice(correctChoices, List.of(new Choice("Answer 1"))));

        assertThat(question.isSingleChoice())
                .isFalse();
    }

    @Test
    void multipleChoiceQuestionWithAllCorrectAnswersReturnsTrue() {
        List<Choice> correctChoices = List.of(new Choice("Answer 1", true), new Choice("Answer 2", true));

        final Question question = new Question("Question 1",
                new MultipleChoice(correctChoices, List.of(new Choice("Answer 1", true), new Choice("Answer 2", true), new Choice("Answer 3", false))));

        boolean correctAnswer = question.isCorrectAnswer(new Choice("Answer 1", true), new Choice("Answer 2", true));

        assertThat(correctAnswer)
                .isTrue();
    }

    @Test
    void singleChoiceWithCorrectAnswerReturnsTrue() {

        final Question question = new Question("Question 1",
                new SingleChoice(new Choice("Answer 1", true), List.of(new Choice("Answer 1", true), new Choice("Answer 2", false), new Choice("Answer 3", false))));

        boolean correctAnswer = question.isCorrectAnswer(new Choice("Answer 1", true));

        assertThat(correctAnswer).isTrue();
    }

    @Test
    void singleChoiceWithTwoAnswersReturnsFalse() {

        final Question question = new Question("Question 1",
                new SingleChoice(new Choice("Answer 1", true), List.of(new Choice("Answer 1", true), new Choice("Answer 2", true), new Choice("Answer 3", true))));

        boolean correctAnswer = question.isCorrectAnswer(new Choice("Answer 1", true), new Choice("Answer 2", false));

        assertThat(correctAnswer)
                .isFalse();
    }
}