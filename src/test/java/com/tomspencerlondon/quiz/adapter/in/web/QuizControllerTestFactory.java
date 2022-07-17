package com.tomspencerlondon.quiz.adapter.in.web;

import com.tomspencerlondon.quiz.adapter.in.web.answer.QuizController;
import com.tomspencerlondon.quiz.hexagon.application.QuizSessionService;
import com.tomspencerlondon.quiz.hexagon.application.port.*;
import com.tomspencerlondon.quiz.hexagon.domain.Question;
import com.tomspencerlondon.quiz.hexagon.domain.Quiz;

import com.tomspencerlondon.quiz.hexagon.domain.domain.factories.MultipleChoiceQuestionTestFactory;

import java.util.List;

import org.jetbrains.annotations.NotNull;

public class QuizControllerTestFactory {
    public static QuizController createAndStartQuizControllerWithOneSingleChoiceQuestion() {
        QuestionRepository questionRepository = QuestionRepositoryFactory.createQuestionRepositoryWithSingleChoiceQuestion();
        QuizRepository quizRepository = createQuizRepositoryWithOneQuizWith(questionRepository.findAll().get(0));
        StubTokenGenerator stubIdGenerator = new StubTokenGenerator();

        QuizSessionService quizSessionService =
                new QuizSessionService(
                        new InMemoryQuizSessionRepository(),
                        quizRepository,
                        stubIdGenerator);
        return new QuizController(quizSessionService, questionRepository);
    }

    @NotNull
    public static QuizRepository createQuizRepositoryWithOneQuizWith(Question question) {
        QuizRepository quizRepository = new InMemoryQuizRepository();
        quizRepository.save(new Quiz("Test Quiz", List.of(question.getId())));
        return quizRepository;
    }

    public static QuizController createAndStartChoiceQuizControllerWithOneMultipleChoiceQuestion() {
        QuestionRepository questionRepository = new InMemoryQuestionRepository();
        Question savedQuestion = questionRepository.save(MultipleChoiceQuestionTestFactory.multipleChoiceQuestion());
        StubTokenGenerator stubIdGenerator = new StubTokenGenerator();
        QuizSessionService quizSessionService = new QuizSessionService(new InMemoryQuizSessionRepository(), createQuizRepositoryWithOneQuizWith(savedQuestion), stubIdGenerator);
        return new QuizController(quizSessionService, questionRepository);
    }
}
