package com.tomspencerlondon.quiz.hexagon.application.port;

import com.tomspencerlondon.quiz.hexagon.domain.Quiz;
import com.tomspencerlondon.quiz.hexagon.domain.QuizId;

import java.util.List;
import java.util.Optional;

public interface QuizRepository {
    Quiz save(Quiz quiz);
    List<Quiz> findAll();

    Optional<Quiz> findById(QuizId quizId);
}
