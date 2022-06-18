package com.example.quiz.hexagon.application;

import com.example.quiz.hexagon.application.port.QuizRepository;
import com.example.quiz.hexagon.application.port.QuizSessionRepository;
import com.example.quiz.hexagon.application.port.TokenGenerator;
import com.example.quiz.hexagon.domain.*;

public class QuizSessionService {
    private final QuizSessionRepository quizSessionRepository;
    private final QuizRepository quizRepository;
    private final TokenGenerator tokenGenerator;

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