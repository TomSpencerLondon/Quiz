package com.example.quiz.domain;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class MultipleChoiceQuestionTestFactory {
    @NotNull
    public static Question multipleChoiceQuestion() {
        final List<Choice> choices = List.of(new Choice("Answer 1"), new Choice("Answer 2"),
                new Choice("Answer 3"), new Choice("Answer 4"));
        List<Choice> correctChoices = List.of(new Choice("Answer 1"), new Choice("Answer 2"));
        MultipleChoice multipleChoice = new MultipleChoice(correctChoices, choices);
        Question multipleChoiceQuestion = new Question("Question 1", multipleChoice);
        return multipleChoiceQuestion;
    }
}
