package com.example.quiz.domain;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class QuestionTest {

    @Test
    void questionWithSingleChoiceIsSingleChoice() {
        final Question question = new Question("Question 1",
                new SingleChoice(List.of(new Choice("Answer 1", true))));

        assertThat(question.isSingleChoice())
                .isTrue();
    }

    @Test
    void questionWithMultipleChoiceIsMultipleChoice() {

        final Question question = new Question("Question 1",
                new MultipleChoice(List.of(new Choice("Answer 1", true), new Choice("Answer 2", true), new Choice("Answer 3", false))));

        assertThat(question.isSingleChoice())
                .isFalse();
    }

    @Test
    void multipleChoiceQuestionWithAllCorrectAnswersReturnsTrue() {
        final Question question = new Question("Question 1",
                new MultipleChoice(List.of(new Choice("Answer 1", true), new Choice("Answer 2", true), new Choice("Answer 3", false))));

        boolean correctAnswer = question.isCorrectAnswer(new Choice("Answer 1", true), new Choice("Answer 2", true));

        assertThat(correctAnswer)
                .isTrue();
    }

    @Test
    void singleChoiceWithCorrectAnswerReturnsTrue() {

        final Question question = new Question("Question 1",
                new SingleChoice(List.of(new Choice("Answer 1", true), new Choice("Answer 2", false), new Choice("Answer 3", false))));

        boolean correctAnswer = question.isCorrectAnswer(new Choice("Answer 1", true));

        assertThat(correctAnswer).isTrue();
    }

    @Test
    void singleChoiceWithTwoAnswersReturnsFalse() {

        final Question question = new Question("Question 1",
                new SingleChoice(List.of(new Choice("Answer 1", true), new Choice("Answer 2", true), new Choice("Answer 3", true))));

        boolean correctAnswer = question.isCorrectAnswer(new Choice("Answer 1", true), new Choice("Answer 2", false));

        assertThat(correctAnswer)
                .isFalse();
    }
}