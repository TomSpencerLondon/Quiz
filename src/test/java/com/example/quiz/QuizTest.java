package com.example.quiz;

import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class QuizTest {
    @Test
    void new_quiz_hasNoQuestions() {
        // Given / when
        Quiz quiz = new Quiz();

        // Then
        List<Question> questions = quiz.questions();

        assertThat(questions)
                .isEmpty();
    }

    @Test
    void new_quiz_hasOneQuestion() {
        // Given / when
        List<Answer> choices = List.of(new Answer("Answer 1"), new Answer("Answer 2"));

        Quiz quiz = new Quiz(new Question(
                "Question 1",
                new MultipleChoice(
                        new Answer("Answer 2"),
                        choices
                )
        ));

        List<Question> questions = quiz.questions();

        assertThat(questions).containsOnly(
                new Question("Question 1",
                new MultipleChoice(
                        new Answer("Answer 2"),
                        List.of(
                                new Answer("Answer 1"),
                                new Answer("Answer 2")
        ))));
    }

    @Test
    void new_quiz_hasManyQuestions() {
        // Given / when
        Quiz quiz = new Quiz(
                new Question(
                        "Question 1",
                        new MultipleChoice(
                                new Answer("Answer 1"),
                                Collections.emptyList())
                ),
                new Question("Question 2",
                        new MultipleChoice(
                                new Answer("Answer 2"),
                                Collections.emptyList())
                ));

        List<Question> questions = quiz.questions();

        assertThat(questions).containsOnly(
                new Question("Question 1",
                        new MultipleChoice(
                                new Answer("Answer 1"),
                                Collections.emptyList())
                ),
                new Question("Question 2",
                        new MultipleChoice(
                                new Answer("Answer 2"),
                                Collections.emptyList()
                        )
                )
        );
    }
}
