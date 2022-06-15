package com.example.quiz.domain;

import com.example.quiz.application.port.InMemoryQuizSessionRepository;
import com.example.quiz.application.port.QuizSessionRepository;

public class QuizSessionBuilder {
    private String token = "stub-token-1";
    private Question question;
    private Quiz quiz;
    private QuizSessionId quizSessionId;
    QuizSessionRepository quizSessionRepository = new InMemoryQuizSessionRepository();
    private QuizSession savedQuizSession;

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

    public QuizSession save() {
        savedQuizSession = quizSessionRepository.save(build());
        return savedQuizSession;
    }

    public QuizSessionBuilder withToken(String token) {
        this.token = token;
        return this;
    }

    public QuizSessionRepository quizSessionRepository() {
        return quizSessionRepository;
    }
}
