package com.example.quiz;

import static org.apache.commons.lang3.builder.EqualsBuilder.reflectionEquals;
import static org.apache.commons.lang3.builder.HashCodeBuilder.reflectionHashCode;

public class FinalMark {
    private int pending;
    private int correct;
    private int incorrect;

    public FinalMark(int pending, int correct, int incorrect) {
        this.pending = pending;
        this.correct = correct;
        this.incorrect = incorrect;
    }

    @Override
    public boolean equals(Object other) {
        return reflectionEquals(this, other);
    }

    @Override
    public int hashCode() {
        return reflectionHashCode(this);
    }

    public int pending(){
        return this.pending;
    }

    public int correct() {
        return this.correct;
    }

    public int incorrect(){
        return this.incorrect;
    }
}
