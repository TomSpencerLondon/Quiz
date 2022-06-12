package com.example.quiz.domain;

import static java.util.function.Predicate.not;

import com.example.quiz.application.port.InMemoryQuestionRepository;
import com.example.quiz.application.port.QuestionRepository;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.function.Predicate;

public class QuestionBuilder {
    private Question savedQuestion;
    private QuestionId questionId;
    private ChoiceType choiceType;
    private QuestionRepository questionRepository = new InMemoryQuestionRepository();

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

    public Question save() {
        savedQuestion = questionRepository.save(build());
        return savedQuestion;
    }

    public QuestionBuilder withQuestionId(long id) {
        questionId = QuestionId.of(id);
        return this;
    }

    public ChoiceId firstChoiceIdForQuestion() {
        Choice choice = savedQuestion
                .choices()
                .stream()
                .findFirst()
                .orElseThrow(NoSuchElementException::new);
        return choice.getId();
    }

    public ChoiceId correctChoiceForQuestion() {
        return questionChoiceMatching(Choice::isCorrect);
    }

    public ChoiceId incorrectChoiceForQuestion() {
        return questionChoiceMatching(not(Choice::isCorrect));
    }

    public QuestionBuilder withQuestionRepository(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
        return this;
    }

    private ChoiceId questionChoiceMatching(Predicate<Choice> predicate) {
        return savedQuestion.choices()
                            .stream()
                            .filter(predicate)
                            .map(Choice::getId)
                            .findFirst()
                            .orElseThrow(NoSuchElementException::new);
    }
}
