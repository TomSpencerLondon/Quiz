package com.example.quiz.domain;

import java.util.List;

public class QuestionBuilder {
    private ChoiceType choiceType;

    public QuestionBuilder withSingleChoice() {
        choiceType = new SingleChoice(List.of(new Choice("Answer 1", true), new Choice("Answer 2", false), new Choice("Answer 3", false)));
        return this;
    }

    public QuestionBuilder withDefaultMultipleChoice() {
        return withMultipleChoice(List.of(new Choice("Answer 1", true), new Choice("Answer 2", true), new Choice("Answer 3", false)));
    }

    public QuestionBuilder withMultipleChoice(List<Choice> choices) {
        choiceType = new MultipleChoice(choices);
        return this;
    }

    public Question build() {
        final Question question = new Question("Question 1",
                choiceType);
        return question;
    }
}
