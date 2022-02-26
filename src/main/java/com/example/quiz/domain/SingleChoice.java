package com.example.quiz.domain;

import java.util.List;

public class SingleChoice {
    private Choice correct;
    private List<Choice> choices;

    public SingleChoice(Choice correct, List<Choice> choices) {
        if (!choices.contains(correct)) {
            throw new IllegalArgumentException(String.format(
                    "'%s' is not among %s",
                    correct.text(),
                    choices
            ));
        }

        this.correct = correct;
        this.choices = choices;
    }

    public List<Choice> choices() {
        return choices;
    }

    public boolean isCorrect(Choice... choices) {
        return correct.equals(choices[0]);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        SingleChoice that = (SingleChoice) o;

        if (!correct.equals(that.correct)) {
            return false;
        }
        return choices.equals(that.choices);
    }

    @Override
    public int hashCode() {
        int result = correct.hashCode();
        result = 31 * result + choices.hashCode();
        return result;
    }
}
