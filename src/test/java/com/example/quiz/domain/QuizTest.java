package com.example.quiz.domain;

import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class QuizTest {

    @Test
    void new_quiz_hasNoQuestions() {
        // Given / when
        Quiz quiz = new Quiz(Collections.EMPTY_LIST);

        // Then
        List<Question> questions = quiz.questions();

        assertThat(questions)
                .isEmpty();
    }

    @Test
    void new_quiz_hasOneQuestion() {
        // Given / when
        List<Choice> choices = List.of(new Choice("Answer 1", true), new Choice("Answer 2", false));
        final Question question = new Question(
                "Question 1",
                new SingleChoice(choices)
        );
        List<Question> questions = List.of(question);
        Quiz quiz = new Quiz(questions);
        List<Question> questionsResult = quiz.questions();

        assertThat(questionsResult).containsOnly(question);
    }
}
