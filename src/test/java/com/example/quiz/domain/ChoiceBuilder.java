package com.example.quiz.domain;

import java.util.ArrayList;
import java.util.List;

public class ChoiceBuilder {

    private final List<Choice> choices = new ArrayList<>();
    private int choiceCounter = 1;

    public ChoiceBuilder withCorrectChoice() {
        addChoice(true);
        return this;
    }

    public ChoiceBuilder withIncorrectChoice() {
        addChoice(false);
        return this;
    }

    public List<Choice> asList() {
        return choices;
    }

    public Choice[] asArray() {
        return choices.toArray(Choice[]::new);
    }

    private void addChoice(boolean isCorrect) {
        choices.add(new Choice(ChoiceId.of(choiceCounter), "Answer " + choiceCounter, isCorrect));
        choiceCounter++;
    }
}
