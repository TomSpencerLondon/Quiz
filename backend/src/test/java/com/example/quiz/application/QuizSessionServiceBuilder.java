package com.example.quiz.application;

import com.example.quiz.adapter.in.web.answer.StubTokenGenerator;
import com.example.quiz.application.port.*;
import com.example.quiz.domain.Question;
import com.example.quiz.domain.Quiz;
import com.example.quiz.domain.QuizId;
import com.example.quiz.domain.factories.SingleChoiceQuestionTestFactory;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class QuizSessionServiceBuilder {
    private QuizId quizId;
    private QuizSessionRepository quizSessionRepository;
    private Quiz quiz;

    public QuizId quizId() {
        return quizId;
    }

    public Quiz quiz() {
        return quiz;
    }

    public QuizSessionRepository quizSessionRepository() {
        return quizSessionRepository;
    }

    public QuizSessionServiceBuilder() {
    }

    @NotNull QuizSessionService build() {
        Question question = SingleChoiceQuestionTestFactory.createSingleChoiceQuestion();
        QuestionRepository questionRepository = new InMemoryQuestionRepository();
        questionRepository.save(question);
        QuizRepository quizRepository = new InMemoryQuizRepository();
        quiz = quizRepository.save(new Quiz("Quiz 1", List.of(question.getId())));
        quizId = quiz.getId();

        quizSessionRepository = new InMemoryQuizSessionRepository();
        return new QuizSessionService(quizSessionRepository, quizRepository, new StubTokenGenerator());
    }
}