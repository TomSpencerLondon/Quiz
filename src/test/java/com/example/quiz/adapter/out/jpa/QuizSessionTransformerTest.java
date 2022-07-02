package com.example.quiz.adapter.out.jpa;

import com.example.quiz.hexagon.application.port.InMemoryQuestionRepository;
import com.example.quiz.hexagon.domain.*;
import com.example.quiz.hexagon.domain.domain.QuestionBuilder;
import com.example.quiz.hexagon.domain.domain.QuizSessionBuilder;
import com.example.quiz.hexagon.domain.domain.ResponseBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class QuizSessionTransformerTest {
    InMemoryQuestionRepository questionRepository;
    ResponseTransformer responseTransformer;
    QuizSessionTransformer quizSessionTransformer;

    @BeforeEach
    void setUp() {
        questionRepository = new InMemoryQuestionRepository();
        responseTransformer = new ResponseTransformer(questionRepository);
        quizSessionTransformer = new QuizSessionTransformer(questionRepository, responseTransformer);
    }

    @Test
    void quizSessionDboToQuizSession() {
        // given
        Question question = new QuestionBuilder()
                .withQuestionId(1L)
                .withDefaultSingleChoice().build();
        Quiz quiz = new Quiz("Quiz 1", List.of(question.getId()));
        quiz.setId(QuizId.of(1L));
        QuizSession expected = new QuizSessionBuilder()
                .withId(1L)
                .withQuiz(quiz)
                .withQuestion(question).build();

        QuizSessionDbo quizSessionDbo = new QuizSessionDboBuilder()
                .withId(1L)
                .withToken("stub-token-1")
                .withQuizId(quiz.getId())
                .withCurrentQuestionId(question.getId())
                .build();

        // when
        QuizSession quizSession = quizSessionTransformer.toQuizSession(quizSessionDbo);

        // then

        assertThat(quizSession)
                .usingRecursiveComparison()
                .ignoringFields("question")
                .isEqualTo(expected);
    }

    @Test
    void quizSessionToQuizSessionDbo() {
        // given
        Question question = new QuestionBuilder()
                .withQuestionId(0L)
                .withDefaultSingleChoice()
                .build();

        Quiz quiz = new Quiz("Quiz 1", List.of(question.getId()));
        quiz.setId(QuizId.of(1L));

        QuizSession quizSession = new QuizSessionBuilder()
                .withId(1L)
                .withQuestion(question)
                .withQuiz(quiz)
                .build();

        quizSession.respondWith(question, quiz, question.choices().get(0).getId().id());

        Response response = new ResponseBuilder()
                .withQuestion(question)
                .withCorrectChoice().build();

        QuizSessionDbo expected = new QuizSessionDboBuilder()
                .withId(1L)
                .withQuizId(quiz.getId())
                .withToken("stub-token-1")
                .withCurrentQuestionId(question.getId())
                .withResponse(response)
                .build();

        // when
        QuizSessionDbo quizSessionDbo = quizSessionTransformer.toQuizSessionDbo(quizSession);

        // then
        assertThat(quizSessionDbo)
                .usingRecursiveComparison()
                .isEqualTo(expected);
    }
}
