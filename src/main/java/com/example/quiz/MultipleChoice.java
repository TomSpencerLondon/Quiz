package com.example.quiz;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.apache.commons.lang3.builder.EqualsBuilder.reflectionEquals;
import static org.apache.commons.lang3.builder.HashCodeBuilder.reflectionHashCode;

public class MultipleChoice {
    private Answer correct;
    private List<Answer> answers = new ArrayList<>();

    public MultipleChoice(Answer correct, List<Answer> answers) {
        this.correct = correct;
        this.answers = answers;
    }

    public MultipleChoice() {
    }

    @Override
    public boolean equals(Object other) {
        return reflectionEquals(this, other);
    }

    @Override
    public int hashCode() {
        return reflectionHashCode(this);
    }

    public Optional<Answer> correctAnswer(){
        return Optional.of(this.correct);
    }

    public List<Answer> answers(){
        return this.answers;
    }
}
