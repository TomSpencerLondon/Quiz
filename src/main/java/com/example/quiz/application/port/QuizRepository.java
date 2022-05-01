package com.example.quiz.application.port;

import com.example.quiz.domain.Quiz;

public interface QuizRepository {
    Quiz save(Quiz quiz);
}
