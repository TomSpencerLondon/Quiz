package com.example.quiz.domain;

public class UnfinishedQuizSession extends QuizSession {
    public UnfinishedQuizSession(String unfinished) {
        setToken(unfinished);
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}
