package com.example.quiz.application.port;

import com.example.quiz.domain.Quiz;
import com.example.quiz.domain.QuizId;

import java.util.List;
import java.util.Optional;

public interface QuizRepository {
    Quiz save(Quiz quiz);
    List<Quiz> findAll();

    Optional<Quiz> findById(QuizId quizId);
}
