package com.example.quiz.domain;

public class QuizNotFound extends RuntimeException {
    public QuizNotFound() {
        super();
    }

    public QuizNotFound(String message) {
        super(message);
    }
}
