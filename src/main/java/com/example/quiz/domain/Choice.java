package com.example.quiz.domain;

public class Choice {
    private String text;

    public Choice(String text) {
        this.text = text;
    }

    public String text() {
      return this.text;
    }

    @Override
    public String toString() {
        return this.text;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Choice choice = (Choice) o;

        return text.equals(choice.text);
    }

    @Override
    public int hashCode() {
        return text.hashCode();
    }
}
