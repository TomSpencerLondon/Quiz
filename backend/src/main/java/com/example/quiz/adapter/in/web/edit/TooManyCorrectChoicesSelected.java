package com.example.quiz.adapter.in.web.edit;

import java.util.List;

class TooManyCorrectChoicesSelected extends RuntimeException {

    TooManyCorrectChoicesSelected(String message) {
        super(message);
    }

    TooManyCorrectChoicesSelected(ChoiceForm... choices) {
        this(String.format("Too many choices (%s,%s,%s,%s) are marked as correct", choices));
    }

    TooManyCorrectChoicesSelected(List<String> correctChoices) {
        this(String.format("Too many choices (%s) are marked as correct", correctChoices));
    }
}