package com.example.quiz.domain;

import java.util.List;
import java.util.stream.Stream;

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
        return Stream.of(choices).count()
                == this.choices.stream().filter(Choice::isCorrect).count();
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
