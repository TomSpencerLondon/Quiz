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
        List<Choice> correctChoices = List.of(new Choice("Answer 1"), new Choice("Answer 2"));

        final Question question = new Question("Question 1",
                new MultipleChoice(correctChoices, List.of(new Choice("Answer 1"), new Choice("Answer 2"), new Choice("Answer 3"))));

        boolean correctAnswer = question.isCorrectAnswer(new Choice("Answer 1"), new Choice("Answer 2"));

        assertThat(correctAnswer)
                .isTrue();
    }

    @Test
    void singleChoiceWithCorrectAnswerReturnsTrue() {

        final Question question = new Question("Question 1",
                new SingleChoice(new Choice("Answer 1"), List.of(new Choice("Answer 1"), new Choice("Answer 2"), new Choice("Answer 3"))));

        boolean correctAnswer = question.isCorrectAnswer(new Choice("Answer 1"));

        assertThat(correctAnswer)
                .isTrue();
    }

    @Test
    void singleChoiceWithTwoAnswersReturnsFalse() {

        final Question question = new Question("Question 1",
                new SingleChoice(new Choice("Answer 1"), List.of(new Choice("Answer 1"), new Choice("Answer 2"), new Choice("Answer 3"))));

        boolean correctAnswer = question.isCorrectAnswer(new Choice("Answer 1"), new Choice("Answer 2"));

        assertThat(correctAnswer)
                .isFalse();
    }
}