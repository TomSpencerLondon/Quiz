package com.example.quiz.adapter.out.jpa;

import com.example.quiz.domain.*;

import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class QuizSessionDboBuilder {
    private QuizSessionDbo quizSessionDbo = new QuizSessionDbo();
    private QuestionId currentQuestionId;
    private List<ResponseDbo> responseDboList = new ArrayList<>();

    public QuizSessionDboBuilder withId(long id) {
        quizSessionDbo.setId(id);
        return this;
    }

    public QuizSessionDboBuilder withToken(String token) {
        quizSessionDbo.setToken(token);
        return this;
    }

    public QuizSessionDboBuilder withCurrentQuestionId(QuestionId questionId) {
        this.currentQuestionId = questionId;
        quizSessionDbo.setCurrentQuestionId(questionId.id());
        return this;
    }

    public QuizSessionDbo build() {
        quizSessionDbo.setResponses(responseDboList);
        return quizSessionDbo;
    }

    public QuizSessionDboBuilder withResponse(Response response) {
        ResponseDbo responseDbo = new ResponseDbo();
        responseDbo.setQuestionId(currentQuestionId.id());
        Set<Long> choiceIds = response.choices()
                                    .stream()
                                    .map(Choice::getId)
                                    .mapToLong(ChoiceId::id).boxed().collect(Collectors.toSet());
        responseDbo.setChoiceIds(choiceIds);
        responseDboList.add(responseDbo);
        return this;
    }

    public QuizSessionDboBuilder withQuizId(QuizId quizId) {
        quizSessionDbo.setQuizId(quizId.id());
        return this;
    }

    public QuizSessionDboBuilder withDefaultStartedAt() {
        quizSessionDbo.setStartedAt(ZonedDateTime.of(2022, 3, 10, 5, 10, 0, 0, ZoneOffset.UTC));
        return this;
    }

}
