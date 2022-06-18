package com.example.quiz.hexagon.application.port;

import com.example.quiz.hexagon.domain.Quiz;
import com.example.quiz.hexagon.domain.QuizId;

import java.util.List;
import java.util.Optional;

public interface QuizRepository {
    Quiz save(Quiz quiz);
    List<Quiz> findAll();

    Optional<Quiz> findById(QuizId quizId);
}
