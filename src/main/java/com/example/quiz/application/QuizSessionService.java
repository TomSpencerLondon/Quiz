package com.example.quiz.application;

import com.example.quiz.application.port.QuizRepository;
import com.example.quiz.application.port.QuizSessionRepository;
import com.example.quiz.application.port.TokenGenerator;
import com.example.quiz.domain.*;

public class QuizSessionService {
    private QuizSessionRepository quizSessionRepository;
    private QuizRepository quizRepository;
    private TokenGenerator tokenGenerator;

    public QuizSessionService(QuizSessionRepository quizSessionRepository, QuizRepository quizRepository, TokenGenerator tokenGenerator) {
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

    public String createQuizSession(QuizId quizId) {
        Quiz quiz = quizRepository.findById(quizId)
                                  .orElseThrow(() -> new QuizNotFound("Could not find quizId of " + quizId));
        QuestionId questionId = quiz.firstQuestion();
        String token = tokenGenerator.token();
        QuizSession quizSession = new QuizSession(questionId, token, quizId);
        quizSessionRepository.save(quizSession);
        return token;
    }
}