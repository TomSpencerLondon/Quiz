package com.example.quiz.domain;

import java.util.List;

public class QuestionBuilder {
    private QuestionId questionId;
    private ChoiceType choiceType;

    public QuestionBuilder() {
        questionId = QuestionId.of(1L);
    }

    public QuestionBuilder withDefaultSingleChoice() {
        choiceType = new SingleChoice(new ChoiceBuilder()
                .withCorrectChoice()
                .withIncorrectChoice()
                .withIncorrectChoice()
                .asList());
        return this;
    }

    public QuestionBuilder withDefaultMultipleChoice() {
        return withMultipleChoice(new ChoiceBuilder()
                .withCorrectChoice()
                .withCorrectChoice()
                .withIncorrectChoice()
                .asList());
    }

    public QuestionBuilder withMultipleChoice(List<Choice> choices) {
        choiceType = new MultipleChoice(choices);
        return this;
    }

    public Question build() {
        final Question question = new Question("Question 1",
                choiceType);
        question.setId(questionId);
        return question;
    }

    public QuestionBuilder withQuestionId(long id) {
        questionId = QuestionId.of(id);
        return this;
    }
}
