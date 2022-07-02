package com.tomspencerlondon.quiz.hexagon.domain;

import java.util.List;

public class Grade {
    private int totalQuestions;
    private List<Response> responses;
    private int correct;
    private int incorrect;

    public Grade(List<Response> responses, int correct, int incorrect) {
        this.totalQuestions = responses.size();
        this.responses = responses;
        this.correct = correct;
        this.incorrect = incorrect;
    }

    public int percent() {
        if (totalQuestions == 0){
            return 0;
        }
        return calculatePercent(correct, totalQuestions);
    }

    private int calculatePercent(int correct, int total) {
        final int percent = (int) (100 * ((double) correct / total));
        return percent;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Grade grade = (Grade) o;

        if (totalQuestions != grade.totalQuestions) {
            return false;
        }
        if (correct != grade.correct) {
            return false;
        }
        return incorrect == grade.incorrect;
    }

    @Override
    public int hashCode() {
        int result = totalQuestions;
        result = 31 * result + correct;
        result = 31 * result + incorrect;
        return result;
    }

    @Override
    public String toString() {
        return String.format(
                "Final mark: %s / %s \nCorrect: %s, Incorrect: %s",
                correct,
                totalQuestions,
                correct,
                incorrect
        );
    }

    public int incorrect() {
        return incorrect;
    }

    public int correct() {
        return correct;
    }

    public List<Response> responses() {
        return responses;
    }
}
