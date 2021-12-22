package com.example.quiz.domain.quiz;

import com.example.quiz.domain.Answer;
import com.example.quiz.domain.MultipleChoice;
import com.example.quiz.domain.Question;
import com.example.quiz.domain.ResponseStatus;
import com.example.quiz.domain.port.InMemoryQuestionRepository;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class QuizTest {
    private ResponseStatus PENDING = ResponseStatus.PENDING;

    @Test
    void new_quiz_hasNoQuestions() {
        final InMemoryQuestionRepository questionRepository = new InMemoryQuestionRepository();
        // Given / when
        Quiz quiz = new Quiz(questionRepository);

        // Then
        List<Question> questions = quiz.questions();

        assertThat(questions)
                .isEmpty();
    }

    @Test
    void new_quiz_hasOneQuestion() {
        // Given / when
        List<Answer> choices = List.of(new Answer("Answer 1"), new Answer("Answer 2"));
        final Question question = new Question(
            "Question 1",
            new MultipleChoice(
                new Answer("Answer 2"),
                choices
            )
        );

        final InMemoryQuestionRepository questionRepository = new InMemoryQuestionRepository();
        questionRepository.save(question);

        Quiz quiz = new Quiz(questionRepository);

        List<Question> questions = quiz.questions();

        assertThat(questions).containsOnly(question);
    }
}
