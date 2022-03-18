package com.example.quiz.domain;

import java.util.List;

public class Quiz {
    private final List<Question> questions;

    public Quiz(List<Question> questions) {
        this.questions = questions;
    }

    public List<Question> questions() {
        return questions;
    }

    public QuizSession start() {
        return new QuizSession(this);
    }
}