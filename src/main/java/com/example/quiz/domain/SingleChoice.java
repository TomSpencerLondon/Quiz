package com.example.quiz.domain;

import java.util.List;
import java.util.stream.Stream;

public class SingleChoice implements ChoiceType {
    private Choice correct;
    private List<Choice> choices;

    public SingleChoice(Choice correct, List<Choice> choices) {
        if (!choices.contains(correct)) {
            throw new IllegalArgumentException(String.format(
                    "'%s' is not among %s",
                    correct.text(),
                    choices
            ));
        }

        this.correct = correct;
        this.choices = choices;
    }

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
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        SingleChoice that = (SingleChoice) o;

        if (!correct.equals(that.correct)) {
            return false;
        }
        return choices.equals(that.choices);
    }

    @Override
    public int hashCode() {
        int result = correct.hashCode();
        result = 31 * result + choices.hashCode();
        return result;
    }
}
