package com.tomspencerlondon.quiz.hexagon.domain;

import org.jetbrains.annotations.NotNull;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static java.util.function.Predicate.not;

public class QuizSession {
    private QuizSessionId quizSessionId;
    private ZonedDateTime startedAt;
    private final List<Response> responses;
    private String token;
    private QuizId quizId;
    private QuestionId currentQuestionId;

    // for testing purposes
    public QuizSession() {
        responses = new ArrayList<>();
    }

    public QuizSession(QuestionId currentQuestionId, String token, QuizId quizId) {
        this.currentQuestionId = currentQuestionId;
        this.token = token;
        this.quizId = quizId;
        this.responses = new ArrayList<>();
    }

    @Deprecated
    public QuizSession(QuizSessionId quizSessionId, String token, QuestionId currentQuestionId, List<Response> responses, ZonedDateTime startedAt) {
        this.quizSessionId = quizSessionId;
        this.token = token;
        this.currentQuestionId = currentQuestionId;
        this.responses = responses;
        this.startedAt = startedAt;
    }

    public List<Response> responses() {
        return responses;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public QuestionId currentQuestionId() {
        return currentQuestionId;
    }

    public void respondWith(Question question, Quiz quiz, long... choiceIds) {
        Choice[] selectedChoices = selectedChoicesForQuestion(question, choiceIds);
        boolean correctAnswer = question.isCorrectAnswer(selectedChoices);
        Response response = new Response(question.getId(), correctAnswer, selectedChoices);
        responses.add(response);
        currentQuestionId = quiz.nextQuestionAfter(question.getId());
    }

    @NotNull
    private Choice[] selectedChoicesForQuestion(Question question, long[] choiceIds) {
        List<Long> choiceIdList = Arrays.stream(choiceIds).boxed().toList();
        return question.choices().stream()
                       .filter(c -> choiceIdList.contains(c.getId().id()))
                       .toArray(Choice[]::new);
    }

    public boolean isFinished(Quiz quiz) {
        return responses.size() == quiz.questionCount();
    }

    public int correctResponsesCount() {
        return Math.toIntExact(responses.stream()
                                        .filter(Response::isCorrect)
                                        .count());
    }

    public int incorrectResponsesCount() {
        return Math.toIntExact(responses.stream()
                                        .filter(not(Response::isCorrect))
                                        .count());
    }

    public Grade grade() {
        return new Grade(responses, correctResponsesCount(), incorrectResponsesCount());
    }

    public ZonedDateTime getStartedAt() {
        return startedAt;
    }

    public void setStartedAt(ZonedDateTime startedAt) {
        this.startedAt = startedAt;
    }

    public void setId(QuizSessionId id) {
        this.quizSessionId = id;
    }

    public QuizSessionId getId() {
        return quizSessionId;
    }

    public QuizId quizId() {
        return quizId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        QuizSession that = (QuizSession) o;

        return quizSessionId != null ? quizSessionId.equals(that.quizSessionId) : that.quizSessionId == null;
    }

    @Override
    public int hashCode() {
        return quizSessionId != null ? quizSessionId.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "QuizSession{" +
                " quizSessionId=" + quizSessionId +
                ", responses=" + responses +
                ", token='" + token + '\'' +
                ", quizId=" + quizId +
                ", currentQuestionId=" + currentQuestionId +
                '}';
    }
}