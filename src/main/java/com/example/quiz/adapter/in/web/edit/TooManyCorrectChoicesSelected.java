package com.example.quiz.adapter.in.web.edit;

import java.util.List;

public class TooManyCorrectChoicesSelected extends RuntimeException {

    public TooManyCorrectChoicesSelected(String message) {
        super(message);
    }

    public TooManyCorrectChoicesSelected(ChoiceForm... choices) {
        this(String.format("Too many choices (%s,%s,%s,%s) are marked as correct", choices));
    }

    public TooManyCorrectChoicesSelected(List<String> correctChoices) {
        this(String.format("Too many choices (%s) are marked as correct", correctChoices));
    }
}