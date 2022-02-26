package com.example.quiz.adapter.in.web.edit;

import java.util.List;

public class TooFewCorrectChoicesSelected extends RuntimeException {
    public TooFewCorrectChoicesSelected(String message) {
        super(message);
    }

    public TooFewCorrectChoicesSelected(List<String> correctChoices) {
        this(String.format("Too few choices (%s) are marked as correct", correctChoices));
    }
}
