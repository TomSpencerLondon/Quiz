package com.example.quiz.domain;

public class UnfinishedQuizSession extends QuizSession {
    public UnfinishedQuizSession(String unfinished) {
        setId(unfinished);
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}
