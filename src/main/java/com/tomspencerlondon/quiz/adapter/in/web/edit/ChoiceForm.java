package com.tomspencerlondon.quiz.adapter.in.web.edit;

import javax.validation.constraints.NotBlank;

public class ChoiceForm {
    @NotBlank
    private String choice;
    private boolean correctAnswer;

    public ChoiceForm() {
    }

    public ChoiceForm(String choice, boolean correctAnswer) {
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
