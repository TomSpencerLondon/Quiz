package com.example.quiz.domain;

import com.example.quiz.hexagon.domain.Choice;
import com.example.quiz.hexagon.domain.Grade;
import com.example.quiz.hexagon.domain.Question;
import com.example.quiz.hexagon.domain.Response;
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

    public GradeBuilder withCorrectResponseFor(Question question) {
        Choice[] choices = question.choices().stream()
                                   .filter(Choice::isCorrect)
                                   .toArray(Choice[]::new);
        responses.add(new Response(question.getId(), true, choices));
        return this;
    }

    public GradeBuilder withIncorrectResponseFor(Question question) {
        Choice[] choices = question.choices().stream()
                                   .filter(not(Choice::isCorrect))
                                   .toArray(Choice[]::new);
        responses.add(new Response(question.getId(), false, choices));
        return this;
    }
}
