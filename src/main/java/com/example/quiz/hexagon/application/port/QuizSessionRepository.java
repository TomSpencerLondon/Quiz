package com.example.quiz.hexagon.application.port;

import com.example.quiz.hexagon.domain.QuizSession;

import java.util.Optional;

public interface QuizSessionRepository {
    QuizSession save(QuizSession question);

    Optional<QuizSession> findByToken(String id);
}
