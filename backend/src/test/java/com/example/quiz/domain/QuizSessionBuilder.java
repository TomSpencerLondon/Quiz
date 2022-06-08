package com.example.quiz.domain;

public class QuizSessionBuilder {
    private Question question;
    private Quiz quiz;

    public QuizSessionBuilder withQuestion(Question question) {
        this.question = question;
        return this;
    }


    public QuizSessionBuilder withQuiz(Quiz quiz) {
        this.quiz = quiz;
        return this;
    }


    public QuizSession build() {
        return new QuizSession(question.getId(), "Quiz Session 1", quiz.getId());
    }
}
