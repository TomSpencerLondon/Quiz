package com.example.quiz.application;

import com.example.quiz.application.port.InMemoryQuestionRepository;
import com.example.quiz.application.port.InMemoryQuizSessionRepository;
import com.example.quiz.application.port.QuizSessionRepository;
import com.example.quiz.domain.Question;
import com.example.quiz.domain.QuizId;
import com.example.quiz.domain.QuizSession;
import com.example.quiz.domain.QuizSessionNotFound;
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
}