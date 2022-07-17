package com.tomspencerlondon.quiz.hexagon.domain.domain.factories;

import com.tomspencerlondon.quiz.hexagon.domain.Choice;
import com.tomspencerlondon.quiz.hexagon.domain.ChoiceId;
import com.tomspencerlondon.quiz.hexagon.domain.MultipleChoice;
import com.tomspencerlondon.quiz.hexagon.domain.Question;

import java.util.List;

import org.jetbrains.annotations.NotNull;

public class MultipleChoiceQuestionTestFactory {
    @NotNull
    public static Question multipleChoiceQuestion() {
        final List<Choice> choices = List.of(new Choice(ChoiceId.of(1L), "Answer 1", true), new Choice(ChoiceId.of(2L), "Answer 2", true),
                new Choice(ChoiceId.of(3L), "Answer 3", false), new Choice(ChoiceId.of(4L), "Answer 4", false));
        MultipleChoice multipleChoice = new MultipleChoice(choices);
        return new Question("Question 1", multipleChoice);
    }
}
