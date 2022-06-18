package com.example.quiz.domain;

import com.example.quiz.hexagon.domain.Choice;
import com.example.quiz.hexagon.domain.Question;
import com.example.quiz.hexagon.domain.Response;

public class ResponseBuilder {
    private Question question;
    private ChoiceBuilder choiceBuilder = new ChoiceBuilder();

    public ResponseBuilder() {
    }


    public ResponseBuilder withQuestion(Question question) {
        this.question = question;
        return this;
    }

    public ResponseBuilder withCorrectChoice() {
        choiceBuilder.withCorrectChoice();
        return this;
    }

    public ResponseBuilder withIncorrectChoice() {
        choiceBuilder.withIncorrectChoice();
        return this;
    }

    public Response build() {
        Choice[] choices = choiceBuilder.asArray();
        return new Response(question.getId(),
                question.isCorrectAnswer(choices),
                choices);
    }
}
