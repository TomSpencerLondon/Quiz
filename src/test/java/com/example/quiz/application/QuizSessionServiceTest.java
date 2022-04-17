package com.example.quiz.application;

import com.example.quiz.application.port.InMemoryQuestionRepository;
import com.example.quiz.application.port.InMemoryQuizSessionRepository;
import com.example.quiz.domain.Question;
import com.example.quiz.domain.QuizSession;
import com.example.quiz.domain.factories.SingleChoiceQuestionTestFactory;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class QuizSessionServiceTest {
    @Test
    void startNewSessionCreatesNewQuizSession() {
        // Given
        Question question = SingleChoiceQuestionTestFactory.createSingleChoiceQuestion();
        InMemoryQuestionRepository inMemoryQuestionRepository = new InMemoryQuestionRepository();
        inMemoryQuestionRepository.save(question);
        QuizService quizService = new QuizService(inMemoryQuestionRepository);
        QuizSessionService quizSessionService = new QuizSessionService(quizService, new InMemoryQuizSessionRepository());

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
        QuizSessionService quizSessionService = new QuizSessionService(quizService, new InMemoryQuizSessionRepository());

        // When
        String id = "stub-id-1";
        quizSessionService.startSessionWithToken(id);

        // Then
        QuizSession quizSession = quizSessionService.findSessionByToken("stub-id-1");
        assertThat(quizSession.getToken()).isEqualTo(id);
    }
}