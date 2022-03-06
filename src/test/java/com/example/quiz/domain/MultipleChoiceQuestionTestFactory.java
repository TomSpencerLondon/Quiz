package com.example.quiz.domain;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class MultipleChoiceQuestionTestFactory {
    @NotNull
    public static Question multipleChoiceQuestion() {
        final List<Choice> choices = List.of(new Choice("Answer 1", true), new Choice("Answer 2", true),
                new Choice("Answer 3", false), new Choice("Answer 4", false));
        MultipleChoice multipleChoice = new MultipleChoice(choices);
        return new Question("Question 1", multipleChoice);
    }
}
