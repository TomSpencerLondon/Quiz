package com.tomspencerlondon.quiz.hexagon.domain;

public class QuizNotFound extends RuntimeException {
    public QuizNotFound() {
        super();
    }

    public QuizNotFound(String message) {
        super(message);
    }
}
