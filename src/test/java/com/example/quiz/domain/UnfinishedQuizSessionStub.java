package com.example.quiz.domain;

public class UnfinishedQuizSessionStub extends QuizSession {
    public UnfinishedQuizSessionStub(String unfinished) {
        setToken(unfinished);
    }

    @Override
    public boolean isFinished(Quiz quiz) {
        return false;
    }
}
