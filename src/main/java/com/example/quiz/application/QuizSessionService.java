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

    public String startNewSession() {
        quizSession = quizService.createQuiz().start();
        return quizSession.getId();
    }

    public QuizSession currentSession() {
        return quizSession;
    }

    public QuizSession findSessionById(String id) {
        return quizSessionRepository.findById(id);
    }

    public void startSessionWithId(String id) {
        quizSession = quizService.createQuiz().start();
        quizSession.setId(id);
        quizSessionRepository.save(quizSession);
    }
}