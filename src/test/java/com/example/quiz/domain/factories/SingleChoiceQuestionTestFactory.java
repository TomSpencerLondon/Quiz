package com.example.quiz.domain.factories;

import com.example.quiz.domain.*;

import java.util.List;

public class SingleChoiceQuestionTestFactory {

    public static Question createSingleChoiceQuestion() {
        Question question = new Question(
                "Question 1",
                new SingleChoice(
                        List.of(
                                new Choice(ChoiceId.of(1L), "Answer 1", true),
                                new Choice(ChoiceId.of(2L), "Answer 2", false),
                                new Choice(ChoiceId.of(3L), "Answer 2", false),
                                new Choice(ChoiceId.of(4L), "Answer 2", false)
                        )));
        question.setId(QuestionId.of(1L));
        return question;
    }
}
