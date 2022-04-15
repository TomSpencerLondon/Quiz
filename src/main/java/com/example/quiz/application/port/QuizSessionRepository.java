package com.example.quiz.application.port;

import com.example.quiz.domain.QuizSession;

public interface QuizSessionRepository {
    QuizSession save(QuizSession question);

    QuizSession findByToken(String id);
}
