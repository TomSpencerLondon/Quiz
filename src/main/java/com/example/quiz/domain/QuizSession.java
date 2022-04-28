package com.example.quiz.domain;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import static java.util.function.Predicate.not;

public class QuizSession {

    private Iterator<Question> questionIterator;
    private List<Question> questions;
    private QuizSessionId quizSessionId;
    private ZonedDateTime startedAt;

    private final List<Response> responses;
    private Question question;
    private String token;

    // for testing purposes
    public QuizSession() {
        questionIterator = null;
        questions = null;
        responses = new ArrayList<>();
    }

    public QuizSession(Quiz quiz) {
        questions = quiz.questions();

        if (questions.isEmpty()) {
            throw new IllegalArgumentException();
        }
        questionIterator = questions.iterator();
        question = questionIterator.next();
        responses = new ArrayList<>();
    }

    public QuizSession(QuizSessionId quizSessionId, String token, Question question, List<Response> responses, ZonedDateTime startedAt) {
        this.quizSessionId = quizSessionId;
        this.token = token;
        this.question = question;
        this.responses = responses;
        this.startedAt = startedAt;
    }

    public List<Response> responses() {
        return responses;
    }

    public void addResponse(Response response) {
        responses.add(response);
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

    // trying to replace currentQuestion with currentQuestionId
    @Deprecated
    public Question currentQuestion() {
        return question;
    }

    public QuestionId currentQuestionId() {
        return question.getId();
    }

    public void respondWith(long... choiceIds) {
        List<Long> choiceIdList = Arrays.stream(choiceIds).boxed().toList();
        Choice[] selectedChoices = question.choices().stream()
                                           .filter(c -> choiceIdList.contains(c.getId().id()))
                                           .toArray(Choice[]::new);
        respondWith(selectedChoices);
    }

    public void respondWith(Choice... choices) {
        boolean isCorrect = question.isCorrectAnswer(choices);
        Response response = new Response(question.getId(), isCorrect, choices);
        responses.add(response);
        question = nextQuestion();
    }

    private Question nextQuestion() {
        // quiz.nextQuestionAfter(questionId)
        if (questionIterator.hasNext()) {
            return questionIterator.next();
        }

        return question;
    }

    public boolean isFinished() {
        // quiz.isFinished(questionId)
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

    public void setId(QuizSessionId id) {
        this.quizSessionId = id;
    }

    public QuizSessionId getId() {
        return quizSessionId;
    }
}