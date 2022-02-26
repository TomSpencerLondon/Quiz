package com.example.quiz.adapter.in.web.answer;

public class ChoiceSelection {
    private int index;
    private String text;

    public ChoiceSelection(int index, String text) {
        this.index = index;
        this.text = text;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ChoiceSelection that = (ChoiceSelection) o;

        if (index != that.index) return false;
        return text.equals(that.text);
    }

    @Override
    public int hashCode() {
        int result = index;
        result = 31 * result + text.hashCode();
        return result;
    }
}
