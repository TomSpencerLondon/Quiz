package com.example.quiz;

import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class QuestionTest {
    private QuestionStatus status = QuestionStatus.PENDING;

    @Test
    void knows_noAnswers() {
        // Given
        Question question = new Question("Question 1", status);

        // When
        List<Answer> answers = question.answers();

        // Then
        assertThat(answers).isEmpty();
    }

    @Test
    void knows_OneAnswer() {
        // Given
        Question question = new Question("Question 1",
                new MultipleChoice(
                        new Answer("Answer 1"),
                        Collections.singletonList(
                                new Answer("Answer 1")
                        )), status);

        // When
        List<Answer> answers = question.answers();

        // Then
        assertThat(answers).containsExactly(new Answer("Answer 1"));
    }

    @Test
    void knows_SeveralAnswers() {
        List<Answer> answers = List.of(new Answer("Answer 1"),
                new Answer("Answer 2"),
                new Answer("Answer 3"),
                new Answer("Answer 4"));
        // Given
        Question question = new Question(
                "Question 1",
                new MultipleChoice(
                        new Answer("Answer 1"),
                        answers), status
        );

        // When
        List<Answer> result = question.answers();

        // Then
        assertThat(result)
                .containsExactly(
                        new Answer("Answer 1"),
                        new Answer("Answer 2"),
                        new Answer("Answer 3"),
                        new Answer("Answer 4")
                );
    }

    @Test
    void knows_correctAnswer() {
        // Given
        Question question = new Question(
                "Question 1",
                new MultipleChoice(
                        new Answer("Answer 3"),
                        Collections.emptyList()
                ), status);

        // When
        Answer correctAnswer = question.correctAnswer().get();

        // Then
        assertThat(correctAnswer)
                .isEqualTo(
                        new Answer("Answer 3")
                );
    }

    @Test
    void knows_status() {
        List<Answer> answers = List.of(new Answer("Answer 1"),
                new Answer("Answer 2"),
                new Answer("Answer 3"),
                new Answer("Answer 4"));
        new Question(
                "Question 1",
                new MultipleChoice(
                        new Answer("Answer 1"),
                        answers), status);
    }
}
