package com.example.quiz.application;

import com.example.quiz.domain.QuizSession;

public class QuizSessionService {
    private QuizService quizService;
    private QuizSession quizSession;

    public QuizSessionService(QuizService quizService) {
        this.quizService = quizService;
    }

    public Long startNewSession() {
        quizSession = quizService.createQuiz().start();
        return quizSession.getId();
    }

    public QuizSession currentSession() {
        return quizSession;
    }
}