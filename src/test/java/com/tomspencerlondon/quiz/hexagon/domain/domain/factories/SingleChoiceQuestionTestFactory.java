package com.tomspencerlondon.quiz.hexagon.domain.domain.factories;


import static com.tomspencerlondon.quiz.hexagon.domain.ChoiceId.of;

import com.tomspencerlondon.quiz.hexagon.domain.Choice;
import com.tomspencerlondon.quiz.hexagon.domain.Question;
import com.tomspencerlondon.quiz.hexagon.domain.QuestionId;
import com.tomspencerlondon.quiz.hexagon.domain.SingleChoice;

import java.util.List;

public class SingleChoiceQuestionTestFactory {

    public static Question createSingleChoiceQuestion() {
        Question question = new Question(
                "Question 1",
                new SingleChoice(
                        List.of(
                                new Choice(of(1L), "Answer 1", true),
                                new Choice(of(2L), "Answer 2", false),
                                new Choice(of(3L), "Answer 2", false),
                                new Choice(of(4L), "Answer 2", false)
                        )));
        question.setId(QuestionId.of(1L));
        return question;
    }


    public static Question create(
            String questionText,
            String choice1,
            String choice2,
            String choice3,
            String choice4) {
        return new Question(
                questionText,
                new SingleChoice(List.of(
                        new Choice(of(1L), choice1, true),
                        new Choice(of(2L), choice2, false),
                        new Choice(of(3L), choice3, false),
                        new Choice(of(4L), choice4, false))));
    }
}

