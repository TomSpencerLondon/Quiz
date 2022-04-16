package com.example.quiz.domain;

public record QuizSessionId(long id) {
    public static QuizSessionId of(long id) {
        return new QuizSessionId(id);
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + "=" + id;
    }
}
