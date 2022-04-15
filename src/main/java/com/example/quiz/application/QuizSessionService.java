package com.example.quiz.application;

import com.example.quiz.application.port.QuizSessionRepository;
import com.example.quiz.domain.QuizSession;

public class QuizSessionService {
    private QuizService quizService;
    private QuizSessionRepository quizSessionRepository;
    private QuizSession quizSession;

    public QuizSessionService(QuizService quizService, QuizSessionRepository quizSessionRepository) {
        this.quizService = quizService;
        this.quizSessionRepository = quizSessionRepository;
    }

    public QuizSession findSessionByToken(String id) {
        return quizSessionRepository.findByToken(id);
    }

    public void startSessionWithToken(String id) {
        quizSession = quizService.createQuiz().start();
        quizSession.setToken(id);
        quizSessionRepository.save(quizSession);
    }
}