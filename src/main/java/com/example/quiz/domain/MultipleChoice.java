package com.example.quiz.domain;

import java.util.Arrays;
import java.util.List;

public class MultipleChoice implements ChoiceType {
    private final List<Choice> choices;

    public MultipleChoice(List<Choice> choices) {
        this.choices = choices;
    }

    public List<Choice> choices() {
        return choices;
    }

    @Override
    public boolean isCorrect(Choice... choices) {
        if (choices.length < 2) {
            return false;
        }

        List<Choice> correctChoices = this.choices.stream().filter(Choice::isCorrect).toList();
        List<Choice> playerChoices = Arrays.asList(choices);
        return correctChoices.containsAll(playerChoices);
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
