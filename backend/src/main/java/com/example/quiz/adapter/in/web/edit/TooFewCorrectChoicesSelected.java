package com.example.quiz.adapter.in.web.edit;

import java.util.List;

class TooFewCorrectChoicesSelected extends RuntimeException {
    TooFewCorrectChoicesSelected(String message) {
        super(message);
    }

    TooFewCorrectChoicesSelected(List<String> correctChoices) {
        this(String.format("Too few choices (%s) are marked as correct", correctChoices));
    }
}
