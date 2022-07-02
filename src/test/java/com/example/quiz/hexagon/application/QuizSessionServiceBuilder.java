package com.example.quiz.hexagon.application;

import com.example.quiz.hexagon.application.port.*;
import com.example.quiz.hexagon.domain.*;
import com.example.quiz.hexagon.domain.domain.QuestionBuilder;

import java.util.List;

public class QuizSessionServiceBuilder {
    private QuizSessionRepository quizSessionRepository;
    private QuizRepository quizRepository;
    private QuestionRepository questionRepository;
    private Question question;
    private QuizSession quizSession;

    public QuizSessionServiceBuilder() {
        quizSessionRepository = new InMemoryQuizSessionRepository();
        quizRepository = new InMemoryQuizRepository();
        questionRepository = new InMemoryQuestionRepository();
    }

    public QuizId quizId() {
        return quiz().getId();
    }

    public Quiz quiz() {
        return quizRepository.findAll().get(0);
    }

    public Question question() {
        return question;
    }

    public QuizSessionRepository quizSessionRepository() {
        return quizSessionRepository;
    }

    public QuizSessionService build() {
        return new QuizSessionService(quizSessionRepository, quizRepository, new StubTokenGenerator());
    }

    public QuizSessionServiceBuilder withQuizSession() {
        quizSession = new QuizSession(question.getId(), "stub-id-1", quiz().getId());
        quizSessionRepository.save(quizSession);
        return this;
    }

    public QuizSessionServiceBuilder withQuizRepository(QuizRepository quizRepository) {
        this.quizRepository = quizRepository;
        return this;
    }

    public QuizSessionServiceBuilder withSingleChoiceQuestion() {
        QuestionBuilder questionBuilder = new QuestionBuilder();
        question = questionRepository.save(questionBuilder.withDefaultSingleChoice().build());
        return this;
    }

    public QuizSessionServiceBuilder withMultipleChoiceQuestion() {
        QuestionBuilder questionBuilder = new QuestionBuilder();
        question = questionRepository.save(questionBuilder.withDefaultMultipleChoice().build());
        return this;
    }

    public QuizSessionServiceBuilder withQuiz(long quizId) {
        Quiz quiz = new Quiz("Quiz 1", List.of(question.getId()));
        quiz.setId(QuizId.of(quizId));
        quizRepository.save(quiz);
        return this;
    }

    public QuestionRepository questionRepository() {
        return questionRepository;
    }

    public long[] correctChoicesForQuestion() {
        return question.choices()
                       .stream()
                       .filter(Choice::isCorrect)
                       .map(Choice::getId)
                       .mapToLong(ChoiceId::id)
                       .toArray();
    }
}