package com.example.quiz;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static java.util.Arrays.asList;
import static org.apache.commons.lang3.builder.EqualsBuilder.reflectionEquals;
import static org.apache.commons.lang3.builder.HashCodeBuilder.reflectionHashCode;

public class Question {
    private final MultipleChoice choice;
    private String text;

    public Question(
            String text,
            MultipleChoice choice
    ) {
        this.text = text;
        this.choice = choice;
    }

    public Question(String text) {
        this.text = text;
        this.choice = new MultipleChoice();
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
        return this.text;
    }

    public List<String> answers() {
        if (choice.answers().isPresent()){
            return choice.answers().get();
        } else {
            return Collections.emptyList();
        }
    }

    public String correctAnswer() {
        if (choice.correctAnswer().isPresent()){
            return choice.correctAnswer().get();
        } else {
            return "";
        }
    }
}
