package com.example.quiz.domain.quiz;

import com.example.quiz.domain.Answer;
import com.example.quiz.domain.MultipleChoice;
import com.example.quiz.domain.Question;
import com.example.quiz.domain.ResponseStatus;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class QuizTest {
    private ResponseStatus PENDING = ResponseStatus.PENDING;

    @Test
    void new_quiz_hasNoQuestions() {
        // Given / when
        InMemoryQuiz quiz = new InMemoryQuiz();

        // Then
        List<Question> questions = quiz.questions();

        assertThat(questions)
                .isEmpty();
    }

    @Test
    void new_quiz_hasOneQuestion() {
        // Given / when
        List<Answer> choices = List.of(new Answer("Answer 1"), new Answer("Answer 2"));

        InMemoryQuiz quiz = new InMemoryQuiz(new Question(
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
}
