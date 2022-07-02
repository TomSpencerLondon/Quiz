package com.tomspencerlondon.quiz.adapter.in.api;

import javax.validation.constraints.NotBlank;

public class ChoiceRequest {
    @NotBlank
    private String choice;
    private boolean correctAnswer;

    public ChoiceRequest() {
    }

    public ChoiceRequest(String choice, boolean correctAnswer) {
        this.choice = choice;
        this.correctAnswer = correctAnswer;
    }

    public String getChoice() {
        return choice;
    }

    public void setChoice(String choice) {
        this.choice = choice;
    }

    public boolean isCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(boolean correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    @Override
    public String toString() {
        return "ChoiceForm{" +
                "choice='" + choice + '\'' +
                ", correctAnswer=" + correctAnswer +
                '}';
    }
}
