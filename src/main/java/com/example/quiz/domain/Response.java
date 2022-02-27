package com.example.quiz.domain;

import java.util.List;

public class Response {
    private final Choice choice;
    private final Question question;
    private final List<Choice> choices;

    public Response(Question question, Choice... choices) {
        this.choice = choices[0];
        this.choices = List.of(choices);
        this.question = question;
    }

    public boolean isCorrect() {
        return question.isCorrectAnswer(choice);
    }

    public Choice getChoice() {
        return choice;
    }

    public Question getQuestion() {
        return question;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Response response = (Response) o;

        if (!choice.equals(response.choice)) {
            return false;
        }
        return question.equals(response.question);
    }

    @Override
    public int hashCode() {
        int result = choice.hashCode();
        result = 31 * result + question.hashCode();
        return result;
    }
}
