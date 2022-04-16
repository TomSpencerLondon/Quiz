package com.example.quiz.domain;

public record SessionId(long id) {
    public static SessionId of(long id) {
        return new SessionId(id);
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + "=" + id;
    }
}
