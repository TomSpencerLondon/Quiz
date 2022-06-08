package com.example.quiz.adapter.in.web.edit;

import java.util.List;
import java.util.stream.IntStream;

public class DummyQuestionChoices {

    private List<ChoiceForm> choices;

    public DummyQuestionChoices(int amount) {
        this.choices = IntStream.range(0, amount).mapToObj(ignore -> new ChoiceForm()).toList();
    }

    public DummyQuestionChoices() {
    }

    public List<ChoiceForm> getChoices() {
        return choices;
    }

    public void setChoices(List<ChoiceForm> choices) {
        this.choices = choices;
    }
}
