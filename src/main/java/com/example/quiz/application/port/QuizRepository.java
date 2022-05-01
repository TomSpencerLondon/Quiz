package com.example.quiz.application.port;

import com.example.quiz.domain.Quiz;

import java.util.List;

public interface QuizRepository {
    Quiz save(Quiz quiz);

    List<Quiz> findAll();
}
