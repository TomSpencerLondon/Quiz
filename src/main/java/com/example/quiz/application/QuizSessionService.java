package com.example.quiz.application;

import com.example.quiz.application.port.QuizRepository;
import com.example.quiz.application.port.QuizSessionRepository;
import com.example.quiz.application.port.TokenGenerator;
import com.example.quiz.domain.*;

public class QuizSessionService {
    private QuizService quizService;
    private QuizSessionRepository quizSessionRepository;
    private QuizSession quizSession;
    private QuizRepository quizRepository;
    private TokenGenerator tokenGenerator;

    public QuizSessionService(QuizService quizService, QuizSessionRepository quizSessionRepository, QuizRepository quizRepository, TokenGenerator tokenGenerator) {
        this.quizService = quizService;
        this.quizSessionRepository = quizSessionRepository;
        this.quizRepository = quizRepository;
        this.tokenGenerator = tokenGenerator;
    }

    public QuizSession findSessionByToken(String token) {
        return quizSessionRepository.findByToken(token).orElseThrow(QuizSessionNotFound::new);
    }

    public Quiz findQuizById(QuizId quizId) {
        return quizRepository.findById(quizId).orElseThrow(QuizNotFound::new);
    }

    @Deprecated
    public void startSessionWithToken(String token) {
        quizSession = quizService.createQuiz().start();
        quizSession.setToken(token);
        quizSessionRepository.save(quizSession);
    }

    public String createQuizSession(QuizId quizId) {
        Quiz quiz = quizRepository.findById(quizId).orElseThrow(QuizNotFound::new);
        QuestionId questionId = quiz.firstQuestion();
        String token = tokenGenerator.token();
        QuizSession quizSession = new QuizSession(questionId, token, quizId);
        quizSessionRepository.save(quizSession);
        return token;
    }
}