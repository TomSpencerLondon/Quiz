package com.example.quiz.domain;

import java.util.List;

public class Question {
    private ChoiceType choiceType;
    private final String text;
    private Long id;

    public Question(
            String text,
            ChoiceType choiceType
    ) {
        this.text = text;
        this.choiceType = choiceType;
    }

    public List<Choice> choices() {
        return choiceType.choices();
    }

    public boolean isCorrectAnswer(Choice... choices) {
        return choiceType.isCorrect(choices);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String text() {
        return text;
    }

    public boolean isSingleChoice() {
        return choiceType.isSingleChoice();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.text);

        this.choiceType.choices().forEach((a) -> {
            sb.append("\n");
            sb.append(a);
        });

        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Question question = (Question) o;

        if (!choiceType.equals(question.choiceType)) return false;
        if (!text.equals(question.text)) return false;
        return id.equals(question.id);
    }

    @Override
    public int hashCode() {
        int result = choiceType.hashCode();
        result = 31 * result + text.hashCode();
        result = 31 * result + id.hashCode();
        return result;
    }
}
