package com.example.quiz.domain;

public class FinishedQuizSessionStub extends QuizSession {


    public FinishedQuizSessionStub(QuestionId questionId, String token, QuizId quizId) {
        super(questionId, token, quizId);
    }

    @Override
    public boolean isFinished(Quiz quiz) {
        return true;
    }
}
