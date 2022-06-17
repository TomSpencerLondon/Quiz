package com.example.quiz.application;

import com.example.quiz.adapter.in.web.answer.StubTokenGenerator;
import com.example.quiz.application.port.*;
import com.example.quiz.domain.*;
import org.jetbrains.annotations.NotNull;

import java.util.List;

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