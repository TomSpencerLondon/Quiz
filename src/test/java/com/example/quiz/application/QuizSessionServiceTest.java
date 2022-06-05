package com.example.quiz.application;

import com.example.quiz.adapter.in.web.answer.StubTokenGenerator;
import com.example.quiz.application.port.InMemoryQuestionRepository;
import com.example.quiz.application.port.InMemoryQuizRepository;
import com.example.quiz.application.port.InMemoryQuizSessionRepository;
import com.example.quiz.application.port.QuizSessionRepository;
import com.example.quiz.domain.*;
import com.example.quiz.domain.factories.SingleChoiceQuestionTestFactory;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class QuizSessionServiceTest {

    @Test
    void startNewSessionCreatesNewQuizSession() {
        // Given
        QuizSessionServiceBuilder quizSessionServiceBuilder = new QuizSessionServiceBuilder();
        QuizSessionService quizSessionService = quizSessionServiceBuilder.build();

        // When
        String token = quizSessionService.createQuizSession(quizSessionServiceBuilder.quizId());

        // Then
        QuizSession savedSession = quizSessionService.findSessionByToken("stub-id-1");

        assertThat(savedSession)
                .isNotNull();
        assertThat(savedSession.getToken())
                .isEqualTo(token);
    }

    @Test
    void givenNoQuizThrowsQuizSessionNotFoundException() {
        Question question = SingleChoiceQuestionTestFactory.createSingleChoiceQuestion();
        InMemoryQuestionRepository inMemoryQuestionRepository = new InMemoryQuestionRepository();
        inMemoryQuestionRepository.save(question);
        QuizService quizService = new QuizService(inMemoryQuestionRepository);
        QuizSessionService quizSessionService = new QuizSessionService(new InMemoryQuizSessionRepository(), null, null);

        assertThatThrownBy(() -> quizSessionService.findSessionByToken("stub-id-1"))
                .isInstanceOf(QuizSessionNotFound.class);
    }

    @Test
    void givenQuizIdCreatesNewQuizSessionLinkedToQuiz() {
        // Given
        QuizSessionServiceBuilder quizSessionServiceBuilder = new QuizSessionServiceBuilder();
        QuizSessionService quizSessionService = quizSessionServiceBuilder.build();
        QuizSessionRepository quizSessionRepository = quizSessionServiceBuilder
                .quizSessionRepository();
        QuizId quizId = quizSessionServiceBuilder.quizId();

        // When
        String token = quizSessionService.createQuizSession(quizId);

        // Then
        QuizSession expected = new QuizSession(quizSessionServiceBuilder.quiz().firstQuestion(), token, quizId);
        Optional<QuizSession> quizSession = quizSessionRepository
                .findByToken(token);
        assertThat(quizSession)
                .isPresent()
                .get()
                .usingRecursiveComparison()
                .ignoringFields("quizSessionId")
                .isEqualTo(expected);
    }

    @Test
    void givenNoQuizThrowQuizNotFound() {
        QuizSessionService quizSessionService = new QuizSessionService(new InMemoryQuizSessionRepository(), new InMemoryQuizRepository(), new StubTokenGenerator());

        assertThatThrownBy(() -> quizSessionService.findQuizById(QuizId.of(1L)))
                .isInstanceOf(QuizNotFound.class);
    }

    @Test
    void givenQuizFindsById() {
        InMemoryQuizRepository quizRepository = new InMemoryQuizRepository();
        Question question = new QuestionBuilder().withQuestionId(1L).withDefaultSingleChoice().build();
        Quiz quiz = new QuizBuilder().withId(1L).withQuestions(question).build();
        quizRepository.save(quiz);
        QuizSessionService quizSessionService = new QuizSessionService(new InMemoryQuizSessionRepository(), quizRepository, new StubTokenGenerator());

        Quiz result = quizSessionService.findQuizById(QuizId.of(1L));
        assertThat(result)
                .isEqualTo(quiz);
    }
}