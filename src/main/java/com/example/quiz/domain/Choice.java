package com.example.quiz.domain;

public class Choice {
    private ChoiceId id;
    private final String text;
    private final boolean isCorrect;

    public Choice(String text) {
        this.text = text;
        this.isCorrect = false;
    }

    public Choice(String text, boolean isCorrect) {
        this.text = text;
        this.isCorrect = isCorrect;
    }

    public Choice(ChoiceId id, String text, boolean isCorrect) {
        this.id = id;
        this.text = text;
        this.isCorrect = isCorrect;
    }

    public ChoiceId getId() {
        return id;
    }

    public void setId(ChoiceId id) {
        this.id = id;
    }

    public String text() {
        return this.text;
    }

    public boolean isCorrect() {
        return isCorrect;
    }

    @Override
    public String toString() {
        return "Choice{" +
                "id=" + id +
                ", text='" + text + '\'' +
                ", isCorrect=" + isCorrect +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Choice choice = (Choice) o;

        if (isCorrect != choice.isCorrect) return false;
        return text.equals(choice.text);
    }

    @Override
    public int hashCode() {
        int result = text.hashCode();
        result = 31 * result + (isCorrect ? 1 : 0);
        return result;
    }
}
