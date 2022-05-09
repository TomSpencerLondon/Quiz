package com.example.quiz.application.port;

import com.example.quiz.domain.QuizSession;

import java.util.Optional;

public interface QuizSessionRepository {
    QuizSession save(QuizSession question);

    Optional<QuizSession> findByToken(String id);
}
