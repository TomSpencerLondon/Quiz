package com.example.quiz.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import static java.util.function.Predicate.not;

public class GradeBuilder {
    private List<Response> responses = new ArrayList<>();
    int questionCount = 1;

    public GradeBuilder withCorrectSingleChoice() {
        Question question = new QuestionBuilder()
                .withQuestionId(questionCount)
                .withDefaultSingleChoice()
                .build();
        questionCount++;

        responses.add(new ResponseBuilder()
                .withQuestion(question)
                .withCorrectChoice()
                .build());
        return this;
    }

    public GradeBuilder withIncorrectSingleChoice() {
        Question question = new QuestionBuilder()
                .withQuestionId(questionCount)
                .withDefaultSingleChoice()
                .build();
        questionCount++;

        responses.add(new ResponseBuilder()
                .withQuestion(question)
                .withIncorrectChoice()
                .build());
        return this;
    }

    public Grade build() {
        int correct = countResponsesFor(Response::isCorrect);
        int incorrect = countResponsesFor(not(Response::isCorrect));
        return new Grade(responses, correct, incorrect);
    }

    private int countResponsesFor(Predicate<Response> isCorrect) {
        return Math.toIntExact(responses.stream()
                                        .filter(isCorrect)
                                        .count());
    }
}
