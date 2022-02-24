package com.example.quiz.domain;

import java.util.List;

public class MultipleChoice {

    private List<Choice> correctChoices;
    private List<Choice> choices;

    public MultipleChoice(List<Choice> correctChoices, List<Choice> allChoices) {
        this.correctChoices = correctChoices;
        this.choices = allChoices;
    }

    public List<Choice> choices() {
        return choices;
    }
}
