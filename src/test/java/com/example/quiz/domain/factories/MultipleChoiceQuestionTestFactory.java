package com.example.quiz.domain.factories;

import com.example.quiz.hexagon.domain.Choice;
import com.example.quiz.hexagon.domain.ChoiceId;
import com.example.quiz.hexagon.domain.MultipleChoice;
import com.example.quiz.hexagon.domain.Question;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class MultipleChoiceQuestionTestFactory {
    @NotNull
    public static Question multipleChoiceQuestion() {
        final List<Choice> choices = List.of(new Choice(ChoiceId.of(1L), "Answer 1", true), new Choice(ChoiceId.of(2L), "Answer 2", true),
                new Choice(ChoiceId.of(3L), "Answer 3", false), new Choice(ChoiceId.of(4L), "Answer 4", false));
        MultipleChoice multipleChoice = new MultipleChoice(choices);
        return new Question("Question 1", multipleChoice);
    }
}
