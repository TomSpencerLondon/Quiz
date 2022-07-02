package com.example.quiz.hexagon.domain.domain;

import com.example.quiz.hexagon.domain.Question;
import com.example.quiz.hexagon.domain.Quiz;
import com.example.quiz.hexagon.domain.QuizSession;
import com.example.quiz.hexagon.domain.QuizSessionId;

public class QuizSessionBuilder {
    private String token = "stub-token-1";
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
        QuizSession quizSession = new QuizSession(question.getId(), token, quiz.getId());
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
