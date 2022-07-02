package com.tomspencerlondon.quiz.hexagon.domain;

public record ChoiceId(long id) {
    public static ChoiceId of(long id) {
        return new ChoiceId(id);
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + "=" + id;
    }
}
