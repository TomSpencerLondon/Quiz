package com.example.quiz.domain;

import java.util.List;
import java.util.stream.Stream;

public class SingleChoice implements ChoiceType {
    private List<Choice> choices;

    public SingleChoice(List<Choice> choices) {
        this.choices = choices;
    }

    @Override
    public List<Choice> choices() {
        return choices;
    }

    @Override
    public boolean isCorrect(Choice... choices) {
        return Stream.of(choices).allMatch(Choice::isCorrect);
    }

    @Override
    public boolean isSingleChoice() {
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SingleChoice that = (SingleChoice) o;

        return choices.equals(that.choices);
    }

    @Override
    public int hashCode() {
        return choices.hashCode();
    }
}
