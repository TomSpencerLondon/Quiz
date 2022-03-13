package com.example.quiz.domain;

import java.util.List;

public class Quiz {
    private Long currentSessionId = 0L;
    private final List<Question> questions;

    public Quiz(List<Question> questions) {
        this.questions = questions;
    }

    public List<Question> questions() {
        return questions;
    }

    public QuizSession start() {
        currentSessionId++;
        QuizSession quizSession = new QuizSession(this);
        quizSession.setId(currentSessionId);
        return quizSession;
    }
}