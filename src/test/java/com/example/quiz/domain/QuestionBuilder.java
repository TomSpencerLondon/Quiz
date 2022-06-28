package com.example.quiz.domain;

import com.example.quiz.hexagon.domain.Choice;
import com.example.quiz.hexagon.domain.ChoiceType;
import com.example.quiz.hexagon.domain.MultipleChoice;
import com.example.quiz.hexagon.domain.Question;
import com.example.quiz.hexagon.domain.QuestionId;
import com.example.quiz.hexagon.domain.SingleChoice;

import java.util.List;

public class QuestionBuilder {
    private QuestionId questionId;
    private ChoiceType choiceType;

    public QuestionBuilder() {
    }

    public QuestionBuilder withDefaultSingleChoice() {
        choiceType = new SingleChoice(new ChoiceBuilder()
                .withCorrectChoice()
                .withIncorrectChoice()
                .withIncorrectChoice()
                .asList());
        return this;
    }

    public QuestionBuilder withDefaultSingleChoiceWithoutIds() {
        choiceType = new SingleChoice(new ChoiceBuilder()
                .withCorrectChoiceWithoutId()
                .withIncorrectChoiceWithoutId()
                .withIncorrectChoiceWithoutId()
                .asList());
        return this;
    }

    public QuestionBuilder withSingleChoice(List<Choice> choices) {
        choiceType = new SingleChoice(choices);
        return this;
    }

    public QuestionBuilder withDefaultMultipleChoice() {
        return withMultipleChoice(new ChoiceBuilder()
                .withCorrectChoice()
                .withCorrectChoice()
                .withIncorrectChoice()
                .asList());
    }

    public QuestionBuilder withDefaultMultipleChoiceWithoutIds() {
        choiceType = new MultipleChoice(new ChoiceBuilder()
                .withCorrectChoiceWithoutId()
                .withCorrectChoiceWithoutId()
                .withIncorrectChoiceWithoutId()
                .asList());
        return this;
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
