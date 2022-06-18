package com.example.quiz.hexagon.domain;

import java.util.Arrays;
import java.util.List;

public class MultipleChoice implements ChoiceType {
    private final List<Choice> choices;
    private final List<Choice> correctChoices;

    public MultipleChoice(List<Choice> choices) {
        this.choices = choices;
        correctChoices = this.choices.stream().filter(Choice::isCorrect).toList();
    }

    public List<Choice> choices() {
        return choices;
    }

    @Override
    public boolean isCorrect(Choice... choices) {
        return Arrays.asList(choices).containsAll(correctChoices);
    }

    @Override
    public boolean isSingleChoice() {
        return false;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MultipleChoice that = (MultipleChoice) o;

        return choices.equals(that.choices);
    }

    @Override
    public int hashCode() {
        return choices.hashCode();
    }
}
