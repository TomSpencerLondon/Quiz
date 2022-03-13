package com.example.quiz.domain.factories;

import com.example.quiz.domain.Choice;
import com.example.quiz.domain.Question;
import com.example.quiz.domain.Response;
import com.example.quiz.domain.SingleChoice;

import java.util.List;

public class SingleChoiceQuestionTestFactory {

    public static Question createSingleChoiceQuestion() {
        Question question = new Question(
                "Question 1",
                new SingleChoice(List.of(new Choice("Answer 1", true), new Choice("Answer 2", false))));
        return question;
    }

    public static Response createSingleChoiceQuestionCorrectResponse(Question question) {
        return new Response(question, new Choice("Answer 1"));
    }

    public static Response createSingleChoiceQuestionIncorrectResponse(Question question) {
        return new Response(question, new Choice("Answer 2"));
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
                        new Choice(choice1, true),
                        new Choice(choice2, false),
                        new Choice(choice3, false),
                        new Choice(choice4, false))));
    }
}
