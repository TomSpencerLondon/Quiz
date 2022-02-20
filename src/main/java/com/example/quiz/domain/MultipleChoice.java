package com.example.quiz.domain;

import java.util.List;

public class MultipleChoice {

    private List<Choice> choices;

    public MultipleChoice(List<Choice> choices) {
        this.choices = choices;
    }

    public List<Choice> choices() {
        return choices;
    }
}
