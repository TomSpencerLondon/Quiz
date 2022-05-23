package com.example.quiz.application;

import com.example.quiz.adapter.in.web.answer.StubTokenGenerator;
import com.example.quiz.application.port.InMemoryQuestionRepository;
import com.example.quiz.application.port.InMemoryQuizRepository;
import com.example.quiz.application.port.InMemoryQuizSessionRepository;
import com.example.quiz.domain.*;
import com.example.quiz.domain.factories.SingleChoiceQuestionTestFactory;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class QuizSessionServiceTest {
    @Test
    void startNewSessionCreatesNewQuizSession() {
        // Given
        Question question = SingleChoiceQuestionTestFactory.createSingleChoiceQuestion();
        InMemoryQuestionRepository inMemoryQuestionRepository = new InMemoryQuestionRepository();
        inMemoryQuestionRepository.save(question);
        QuizService quizService = new QuizService(inMemoryQuestionRepository);
        QuizSessionService quizSessionService = new QuizSessionService(quizService, new InMemoryQuizSessionRepository(), null, null);

        // When
        quizSessionService.startSessionWithToken("stub-id-1");

        // Then
        QuizSession savedSession = quizSessionService.findSessionByToken("stub-id-1");
        assertThat(savedSession)
                .isInstanceOf(QuizSession.class);
    }

    @Test
    void startNewSessionReturnsSessionId() {
        // Given
        Question question = SingleChoiceQuestionTestFactory.createSingleChoiceQuestion();
        InMemoryQuestionRepository inMemoryQuestionRepository = new InMemoryQuestionRepository();
        inMemoryQuestionRepository.save(question);
        QuizService quizService = new QuizService(inMemoryQuestionRepository);
        QuizSessionService quizSessionService = new QuizSessionService(quizService, new InMemoryQuizSessionRepository(), null, null);

        // When
        String id = "stub-id-1";
        quizSessionService.startSessionWithToken(id);

        // Then
        QuizSession quizSession = quizSessionService.findSessionByToken("stub-id-1");
        assertThat(quizSession.getToken()).isEqualTo(id);
    }

    @Test
    void givenNoQuizThrowsQuizSessionNotFoundException() {
        Question question = SingleChoiceQuestionTestFactory.createSingleChoiceQuestion();
        InMemoryQuestionRepository inMemoryQuestionRepository = new InMemoryQuestionRepository();
        inMemoryQuestionRepository.save(question);
        QuizService quizService = new QuizService(inMemoryQuestionRepository);
        QuizSessionService quizSessionService = new QuizSessionService(quizService, new InMemoryQuizSessionRepository(), null, null);

        assertThatThrownBy(() -> quizSessionService.findSessionByToken("stub-id-1"))
                .isInstanceOf(QuizSessionNotFound.class);
    }

    @Test
    void givenQuizIdCreatesNewQuizSession() {
        Question question = SingleChoiceQuestionTestFactory.createSingleChoiceQuestion();
        InMemoryQuestionRepository inMemoryQuestionRepository = new InMemoryQuestionRepository();
        inMemoryQuestionRepository.save(question);
        QuizService quizService = new QuizService(inMemoryQuestionRepository);
        InMemoryQuizSessionRepository quizSessionRepository = new InMemoryQuizSessionRepository();
        InMemoryQuizRepository quizRepository = new InMemoryQuizRepository();
        Quiz savedQuiz = quizRepository.save(new Quiz("Stub Quiz", List.of(question.getId())));
        QuizSessionService quizSessionService = new QuizSessionService(quizService, quizSessionRepository, quizRepository, new StubTokenGenerator());

        QuizId quizId = savedQuiz.getId();
        String token = quizSessionService.createQuizSession(quizId);

        Optional<QuizSession> quizSession = quizSessionRepository.findByToken(token);
        assertThat(quizSession.get().currentQuestionId())
                .isEqualTo(savedQuiz.firstQuestion());
    }
}