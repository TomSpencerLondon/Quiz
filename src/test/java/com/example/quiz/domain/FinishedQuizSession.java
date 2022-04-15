package com.example.quiz.domain;

public class FinishedQuizSession extends QuizSession {


    public FinishedQuizSession(String finished) {
        setToken(finished);
    }

    @Override
    public boolean isFinished() {
        return true;
    }
}
