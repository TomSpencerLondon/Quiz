package com.example.quiz.adapter.in.web;

import com.example.quiz.adapter.in.web.answer.QuizController;
import com.example.quiz.adapter.in.web.answer.StubTokenGenerator;
import com.example.quiz.application.QuizService;
import com.example.quiz.application.QuizSessionService;
import com.example.quiz.application.port.*;
import com.example.quiz.domain.Question;
import com.example.quiz.domain.Quiz;
import com.example.quiz.domain.factories.MultipleChoiceQuestionTestFactory;
import com.example.quiz.domain.factories.SingleChoiceQuestionTestFactory;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class QuizControllerTestFactory {
    public static QuizController createAndStartQuizControllerWithOneSingleChoiceQuestion() {
        QuestionRepository questionRepository = createQuestionRepositoryWithSingleChoiceQuestion();
        QuizService quizService = new QuizService(questionRepository);
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
    public static QuestionRepository createQuestionRepositoryWithSingleChoiceQuestion() {
        Question singleChoiceQuestion = SingleChoiceQuestionTestFactory.createSingleChoiceQuestion();
        QuestionRepository questionRepository = new InMemoryQuestionRepository();
        questionRepository.save(singleChoiceQuestion);
        return questionRepository;
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
        QuizService quizService = new QuizService(questionRepository);
        StubTokenGenerator stubIdGenerator = new StubTokenGenerator();
        QuizSessionService quizSessionService = new QuizSessionService(new InMemoryQuizSessionRepository(), createQuizRepositoryWithOneQuizWith(savedQuestion), stubIdGenerator);
        return new QuizController(quizSessionService, questionRepository);
    }
}