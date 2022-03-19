package com.example.quiz.domain;

public class FinishedQuizSession extends QuizSession {

    @Override
    public boolean isFinished() {
        return true;
    }
}
