package com.example.quiz.hexagon.domain;

public record QuizId(long id) {
    public static QuizId of(long id) {
        return new QuizId(id);
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + "=" + id;
    }
}
