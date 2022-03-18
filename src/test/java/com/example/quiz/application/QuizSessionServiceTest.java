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
        Question question = SingleChoiceQuestionTestFactory.createSingleChoiceQuestion();
        InMemoryQuestionRepository inMemoryQuestionRepository = new InMemoryQuestionRepository();
        inMemoryQuestionRepository.save(question);
        QuizService quizService = new QuizService(inMemoryQuestionRepository);
        QuizSessionService quizSessionService = new QuizSessionService(quizService, new InMemoryQuizSessionRepository());

        quizSessionService.startNewSession();
        QuizSession quizSession = quizSessionService.currentSession();

        assertThat(quizSession)
                .isInstanceOf(QuizSession.class);
    }

    @Test
    void startNewSessionReturnsSessionId() {
        Question question = SingleChoiceQuestionTestFactory.createSingleChoiceQuestion();
        InMemoryQuestionRepository inMemoryQuestionRepository = new InMemoryQuestionRepository();
        inMemoryQuestionRepository.save(question);
        QuizService quizService = new QuizService(inMemoryQuestionRepository);
        QuizSessionService quizSessionService = new QuizSessionService(quizService, new InMemoryQuizSessionRepository());

        String id = "stub-id-1";
        quizSessionService.startSessionWithId(id);

        QuizSession quizSession = quizSessionService.findSessionById("stub-id-1");

        assertThat(quizSession.getId()).isEqualTo(id);
    }
}