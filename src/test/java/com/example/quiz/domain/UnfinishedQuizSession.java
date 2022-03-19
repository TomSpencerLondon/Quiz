package com.example.quiz.domain;

public class UnfinishedQuizSession extends QuizSession {
    @Override
    public boolean isFinished() {
        return false;
    }
}
