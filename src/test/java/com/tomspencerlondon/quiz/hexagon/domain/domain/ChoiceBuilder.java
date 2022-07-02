package com.tomspencerlondon.quiz.hexagon.domain.domain;

import com.tomspencerlondon.quiz.hexagon.domain.Choice;
import com.tomspencerlondon.quiz.hexagon.domain.ChoiceId;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.function.Predicate;

import static java.util.function.Predicate.not;

public class ChoiceBuilder {

    private final List<Choice> choices = new ArrayList<>();
    private int choiceCounter = 1;
    private Choice choice;

    public ChoiceBuilder withCorrectChoice() {
        addChoice(ChoiceId.of(choiceCounter), true);
        return this;
    }

    public ChoiceBuilder withCorrectChoiceWithoutId() {
        addChoice(null, true);
        return this;
    }

    public ChoiceBuilder withIncorrectChoice() {
        addChoice(ChoiceId.of(choiceCounter), false);
        return this;
    }

    public ChoiceBuilder withIncorrectChoiceWithoutId() {
        addChoice(null, false);
        return this;
    }

    public List<Choice> asList() {
        return choices;
    }

    public Choice[] asArray() {
        return choices.toArray(Choice[]::new);
    }

    public Choice build() {
        return choice;
    }

    private void addChoice(ChoiceId id, boolean isCorrect) {
        choice = new Choice(id, "Answer " + choiceCounter, isCorrect);
        choices.add(choice);
        choiceCounter++;
    }

    public Choice anyCorrectChoice() {
        return anyChoiceMatching(Choice::isCorrect);
    }

    public Choice anyIncorrectChoice() {
        return anyChoiceMatching(not(Choice::isCorrect));
    }

    private Choice anyChoiceMatching(Predicate<Choice> predicate) {
        return choices.stream()
                      .filter(predicate)
                      .findAny()
                      .orElseThrow(NoSuchElementException::new);
    }
}
