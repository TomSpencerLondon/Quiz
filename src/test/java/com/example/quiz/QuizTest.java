package com.example.quiz;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class QuizTest {
    @Test
    void new_quiz_hasNoQuestions() {
        // Given / when
        Quiz quiz = new Quiz();

        // Then
        List<String> questions = quiz.questions();

        assertThat(questions)
                .isEmpty();
    }

    @Test
    void new_quiz_hasOneQuestion() {
        // Given / when
        Quiz quiz = new Quiz("Question 1");

        List<String> questions = quiz.questions();

        assertThat(questions).containsOnly("Question 1");
    }
}
