package com.example.quiz.domain;

import java.util.Arrays;
import java.util.List;

public class MultipleChoice implements ChoiceType {

    private List<Choice> correctChoices;
    private List<Choice> choices;

    public MultipleChoice(List<Choice> correctChoices, List<Choice> allChoices) {
        this.correctChoices = correctChoices;
        this.choices = allChoices;
    }

    public List<Choice> choices() {
        return choices;
    }

    @Override
    public boolean isCorrect(Choice... choices) {
        return Arrays.asList(choices).equals(correctChoices);
    }
}
