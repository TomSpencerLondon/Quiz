package com.example.quiz.domain;

import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class QuestionChoiceTest {

    @Test
    void knowsOneAnswer() {
        // Given
        Question question = new Question("Question 1",
                new SingleChoice(
                        Collections.singletonList(
                                new Choice("Answer 1", true)
                        )));

        // When
        List<Choice> choices = question.choices();

        // Then
        assertThat(choices).containsExactly(new Choice("Answer 1", true));
    }

    @Test
    void knowsSeveralAnswers() {
        // Given
        List<Choice> choices = List.of(new Choice("Answer 1", true),
                new Choice("Answer 2"),
                new Choice("Answer 3"),
                new Choice("Answer 4"));
        Question question = new Question(
                "Question 1",
                new SingleChoice(choices)
        );

        // When
        List<Choice> result = question.choices();

        // Then
        assertThat(result)
                .containsExactly(
                        new Choice("Answer 1", true),
                        new Choice("Answer 2"),
                        new Choice("Answer 3"),
                        new Choice("Answer 4")
                );
    }

    @Test
    void knowsCorrectAnswer() {
        // Given
        final Choice correct = new Choice("Answer 3", true);
        Question question = new Question(
                "Question 1",
                new SingleChoice(List.of(new Choice("Answer 2", false), new Choice("Answer 3", true))));

        // When
        final boolean correctAnswer = question.isCorrectAnswer(correct);

        // Then
        assertThat(correctAnswer)
                .isTrue();
    }

}
