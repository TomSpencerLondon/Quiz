package com.example.quiz.domain;

import java.util.List;

public class Response {
    private final Question question;
    private final List<Choice> choices;

    public Response(Question question, Choice... choices) {
        this.choices = List.of(choices);
        this.question = question;
    }

    public ResponseId getId() {
        return id;
    }

    public void setId(ResponseId id) {
        this.id = id;
    }

    public boolean isCorrect() {
        return question.isCorrectAnswer(choices.toArray(Choice[]::new));
    }

    public List<Choice> getChoices() {
        return choices;
    }

    public Question getQuestion() {
        return question;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Response response = (Response) o;

        if (!question.equals(response.question)) return false;
        return choices.equals(response.choices);
    }

    @Override
    public int hashCode() {
        int result = question.hashCode();
        result = 31 * result + choices.hashCode();
        return result;
    }
}
