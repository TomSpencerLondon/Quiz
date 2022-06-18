package com.example.quiz.application;

import com.example.quiz.adapter.in.web.answer.StubTokenGenerator;
import com.example.quiz.hexagon.application.QuizSessionService;
import com.example.quiz.hexagon.application.port.InMemoryQuizSessionRepository;
import com.example.quiz.hexagon.application.port.QuizRepository;
import com.example.quiz.hexagon.application.port.QuizSessionRepository;
import com.example.quiz.hexagon.domain.Quiz;
import com.example.quiz.hexagon.domain.QuizId;

public class QuizSessionServiceBuilder {
    private QuizId quizId;
    private QuizSessionRepository quizSessionRepository;
    private QuizRepository quizRepository;
    private Quiz quiz;

    public QuizId quizId() {
        return quiz().getId();
    }

    public Quiz quiz() {
        return quizRepository.findAll().get(0);
    }

    public QuizSessionRepository quizSessionRepository() {
        return quizSessionRepository;
    }

    public QuizSessionServiceBuilder() {
    }

    public QuizSessionService build() {
        quizSessionRepository = new InMemoryQuizSessionRepository();
        return new QuizSessionService(quizSessionRepository, quizRepository, new StubTokenGenerator());
    }

    public QuizSessionServiceBuilder withQuizRepository(QuizRepository quizRepository) {
        this.quizRepository = quizRepository;
        return this;
    }
}