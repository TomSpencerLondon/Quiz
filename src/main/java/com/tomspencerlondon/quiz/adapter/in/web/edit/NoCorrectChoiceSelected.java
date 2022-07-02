package com.tomspencerlondon.quiz.adapter.in.web.edit;

public class NoCorrectChoiceSelected extends RuntimeException {

    public NoCorrectChoiceSelected(String message) {
        super(message);
    }

    public NoCorrectChoiceSelected(ChoiceForm... choices) {
        this(String.format("No choices (%s,%s,%s,%s) are marked as correct", choices));
    }
}
