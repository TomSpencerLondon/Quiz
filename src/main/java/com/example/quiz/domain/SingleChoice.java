package com.example.quiz.domain;

import java.util.List;

public class SingleChoice implements ChoiceType {
    private final Choice correctChoice;
    private List<Choice> choices;

    public SingleChoice(List<Choice> choices) {
        this.choices = choices;
        correctChoice = this.choices.stream().filter(Choice::isCorrect).findFirst().orElseThrow();
    }

    @Override
    public List<Choice> choices() {
        return choices;
    }

    @Override
    public boolean isCorrect(Choice... choices) {
        return correctChoice.equals(choices[0]);
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
