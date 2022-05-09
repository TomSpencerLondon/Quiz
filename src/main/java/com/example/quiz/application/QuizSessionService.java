package com.example.quiz.application;

import com.example.quiz.application.port.QuizSessionRepository;
import com.example.quiz.domain.QuizSession;
import com.example.quiz.domain.QuizSessionNotFound;

public class QuizSessionService {
    private QuizService quizService;
    private QuizSessionRepository quizSessionRepository;
    private QuizSession quizSession;

    public QuizSessionService(QuizService quizService, QuizSessionRepository quizSessionRepository) {
        this.quizService = quizService;
        this.quizSessionRepository = quizSessionRepository;
    }

    public QuizSession findSessionByToken(String token) {
        return quizSessionRepository.findByToken(token).orElseThrow(QuizSessionNotFound::new);
    }

    public void startSessionWithToken(String token) {
        quizSession = quizService.createQuiz().start();
        quizSession.setToken(token);
        quizSessionRepository.save(quizSession);
    }
}