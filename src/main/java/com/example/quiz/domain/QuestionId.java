package com.example.quiz.domain;

public record QuestionId(long id) {
    public static QuestionId of(long id) {
        return new QuestionId(id);
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + "=" + id;
    }
}
