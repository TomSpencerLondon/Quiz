package com.example.quiz.domain;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import static java.util.function.Predicate.not;

public class QuizSession {

    private final Iterator<Question> iterator;
    private final List<Question> questions;
    private QuizSessionId id;
    private ZonedDateTime startedAt;

    private final List<Response> responses = new ArrayList<>();
    private Question question;
    private String token;

    public List<Response> getResponses() {
        return responses;
    }

    public void setResponse(Response response) {
        this.responses.add(response);
    }

    public void setId(QuizSessionId id) {
        this.id = id;
    }

    public QuizSessionId getId() {
        return id;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
    // for testing purposes

    public QuizSession() {
        this.iterator = null;
        this.questions = null;
    }

    public QuizSession(Quiz quiz) {
        questions = quiz.questions();
        if (questions.isEmpty()) {
            throw new IllegalArgumentException();
        }
        iterator = questions.iterator();
        question = iterator.next();
    }

    public Question question() {
        return question;
    }

    public void respondWith(int... indices) {
        Choice[] selectedChoices = Arrays.stream(indices)
                                         .mapToObj(index -> question.choices().get(index))
                                         .toArray(Choice[]::new);
        respondWith(selectedChoices);
    }

    public void respondWith(Choice... choices) {
        boolean isCorrect = question.isCorrectAnswer(choices);
        Response response = new Response(question.getId(), isCorrect, choices);
        responses.add(response);
        if (iterator.hasNext()) {
            question = iterator.next();
        }
    }

    public boolean isFinished() {
        return responses.size() == questions.size();
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
}