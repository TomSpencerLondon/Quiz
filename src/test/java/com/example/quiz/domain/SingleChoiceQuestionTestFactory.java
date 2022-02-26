package com.example.quiz.domain;

import java.util.List;

public class SingleChoiceQuestionTestFactory {

    public static Question createSingleChoiceQuestion() {
        Question question = new Question(
                "Question 1",
                new SingleChoice(new Choice("Answer 1"),
                        List.of(new Choice("Answer 1"), new Choice("Answer 2"))));
        return question;
    }

    public static Response createSingleChoiceQuestionCorrectResponse(Question question) {
        return new Response(question, new Choice("Answer 1"));
    }

    public static Response createSingleChoiceQuestionIncorrectResponse(Question question) {
        return new Response(question, new Choice("Answer 2"));
    }
}
