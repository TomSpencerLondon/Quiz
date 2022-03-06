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
        return Stream.of(choices).allMatch(Choice::isCorrect);
    }

    @Override
    public boolean isSingleChoice() {
        return false;
    }
}
