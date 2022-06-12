package com.example.quiz.domain;

public class QuizSessionBuilder {
    private Question question;
    private Quiz quiz;
    private QuizSessionId quizSessionId;

    public QuizSessionBuilder withQuestion(Question question) {
        this.question = question;
        return this;
    }


    public QuizSessionBuilder withQuiz(Quiz quiz) {
        this.quiz = quiz;
        return this;
    }


    public QuizSession build() {
        QuizSession quizSession = new QuizSession(question.getId(), "stub-token-1", quiz.getId());
        if (quizSessionId != null) {
            quizSession.setId(quizSessionId);
        }
        return quizSession;
    }

    public QuizSessionBuilder withId(long id) {
        quizSessionId = QuizSessionId.of(id);
        return this;
    }
}
