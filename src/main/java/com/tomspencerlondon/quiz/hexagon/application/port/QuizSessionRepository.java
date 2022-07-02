package com.tomspencerlondon.quiz.hexagon.application.port;

import com.tomspencerlondon.quiz.hexagon.domain.QuizSession;

import java.util.Optional;

public interface QuizSessionRepository {
    QuizSession save(QuizSession question);

    Optional<QuizSession> findByToken(String id);
}
