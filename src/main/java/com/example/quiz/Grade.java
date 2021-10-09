package com.example.quiz;

import static org.apache.commons.lang3.builder.EqualsBuilder.reflectionEquals;
import static org.apache.commons.lang3.builder.HashCodeBuilder.reflectionHashCode;

public class Grade {
    private int totalQuestions;
    private FinalMark finalMark;

    public Grade(int totalQuestions, FinalMark finalMark) {
        this.totalQuestions = totalQuestions;
        this.finalMark = finalMark;
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
        final int correct = finalMark.correct();
        return String.format(
                "Final mark: %s / %s \nPending: %s, Correct: %s, Incorrect: %s",
                correct,
                totalQuestions,
                finalMark.pending(),
                correct,
                finalMark.incorrect()
        );
    }
}
