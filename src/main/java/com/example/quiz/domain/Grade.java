package com.example.quiz.domain;

import static org.apache.commons.lang3.builder.EqualsBuilder.reflectionEquals;
import static org.apache.commons.lang3.builder.HashCodeBuilder.reflectionHashCode;

public class Grade {
    private int totalQuestions;
    private int correct;
    private int incorrect;

    public Grade(int totalQuestions, int correct, int incorrect) {
        this.totalQuestions = totalQuestions;
        this.correct = correct;
        this.incorrect = incorrect;
    }

    public int percent() {
        if (totalQuestions == 0){
            return 0;
        }
        return calculatePercent(correct, totalQuestions);
    }

    private int calculatePercent(int correct, int total) {
        final int percent = (int) (100 * ((double) correct / total));
        return percent;
    }

    @Override
    public boolean equals(Object other) {
        return reflectionEquals(this, other);
    }

    @Override
    public int hashCode() {
        return reflectionHashCode(this);
    }

    @Override
    public String toString() {
        return String.format(
                "Final mark: %s / %s \nCorrect: %s, Incorrect: %s",
                correct,
                totalQuestions,
                correct,
                incorrect
        );
    }

    public int incorrect() {
        return incorrect;
    }

    public int correct() {
        return correct;
    }
}
