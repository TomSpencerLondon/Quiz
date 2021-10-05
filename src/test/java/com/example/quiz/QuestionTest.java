package com.example.quiz;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class QuestionTest {
    @Test
    void knows_noAnswers() {
        // Given
        Question question = new Question("Question 1");

        // When
        List<String> answers = question.answers();

        // Then
        assertThat(answers).isEmpty();
    }

    @Test
    void knows_OneAnswer() {
        // Given
        Question question = new Question("Question 1", "Answer 1");

        // When
        List<String> answers = question.answers();

        // Then
        assertThat(answers).containsExactly("Answer 1");
    }

    @Test
    void knows_SeveralAnswers() {
        // Given
        Question question = new Question(
                "Question 1",
                "Answer 1",
                "Answer 2",
                "Answer 3",
                "Answer 4"
        );

        // When
        List<String> answers = question.answers();

        // Then
        assertThat(answers)
                .containsExactly(
                "Answer 1",
                "Answer 2",
                "Answer 3",
                "Answer 4"
        );
    }
}
