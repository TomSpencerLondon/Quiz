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
        final Long correct = finalMark.correct();
        return String.format(
                "Final mark: %s / %s \nCorrect: %s, Incorrect: %s",
                correct,
                totalQuestions,
                correct,
                finalMark.incorrect()
        );
    }
}
