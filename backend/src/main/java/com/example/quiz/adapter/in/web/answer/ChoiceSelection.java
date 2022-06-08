package com.example.quiz.adapter.in.web.answer;

public class ChoiceSelection {
    private final long choiceId;
    private final String text;

    public ChoiceSelection(long choiceId, String text) {
        this.choiceId = choiceId;
        this.text = text;
    }

    public long getChoiceId() {
        return choiceId;
    }

    public String getText() {
        return text;
    }

    @Override
    public String toString() {
        return "ChoiceSelection{" +
                "choiceId=" + choiceId +
                ", text='" + text + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ChoiceSelection that = (ChoiceSelection) o;

        return choiceId == that.choiceId;
    }

    @Override
    public int hashCode() {
        return (int) (choiceId ^ (choiceId >>> 32));
    }
}
