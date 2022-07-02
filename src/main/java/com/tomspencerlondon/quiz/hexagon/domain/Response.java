package com.tomspencerlondon.quiz.hexagon.domain;

import java.util.List;

public class Response {

    private ResponseId id;
    private QuestionId questionId;
    private boolean isCorrect;
    private final List<Choice> choices;

    public Response(QuestionId questionId, boolean isCorrect, Choice... choices) {
        this.questionId = questionId;
        this.isCorrect = isCorrect;
        this.choices = List.of(choices);
    }

    public ResponseId getId() {
        return id;
    }

    public void setId(ResponseId id) {
        this.id = id;
    }

    public boolean isCorrect() {
        return isCorrect;
    }

    public List<Choice> choices() {
        return choices;
    }

    public QuestionId questionId() {
        return questionId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Response response = (Response) o;

        if (!questionId.equals(response.questionId)) return false;
        return choices.equals(response.choices);
    }

    @Override
    public int hashCode() {
        int result = questionId.hashCode();
        result = 31 * result + choices.hashCode();
        return result;
    }
}
