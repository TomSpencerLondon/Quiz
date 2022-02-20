package com.example.quiz.domain;

import com.example.quiz.application.port.InMemoryQuestionRepository;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class QuizTest {

    @Test
    void new_quiz_hasNoQuestions() {
        final InMemoryQuestionRepository questionRepository = new InMemoryQuestionRepository();
        // Given / when
        List<Question> repositoryQuestions = questionRepository.findAll();
        Quiz quiz = new Quiz(repositoryQuestions);

        // Then
        List<Question> questions = quiz.questions();

        assertThat(questions)
                .isEmpty();
    }

    @Test
    void new_quiz_hasOneQuestion() {
        // Given / when
        List<Choice> choices = List.of(new Choice("Answer 1"), new Choice("Answer 2"));
        final Question question = new Question(
            "Question 1",
            new SingleChoice(
                new Choice("Answer 2"),
                choices
            )
        );

        final InMemoryQuestionRepository questionRepository = new InMemoryQuestionRepository();
        questionRepository.save(question);
        List<Question> repositoryQuestions = questionRepository.findAll();
        Quiz quiz = new Quiz(repositoryQuestions);

        List<Question> questions = quiz.questions();

        assertThat(questions).containsOnly(question);
    }
}
