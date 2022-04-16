package com.example.quiz.adapter.in.web;

import com.example.quiz.adapter.in.web.answer.QuizController;
import com.example.quiz.adapter.in.web.answer.StubTokenGenerator;
import com.example.quiz.application.QuizService;
import com.example.quiz.application.QuizSessionService;
import com.example.quiz.application.port.InMemoryQuestionRepository;
import com.example.quiz.application.port.InMemoryQuizSessionRepository;
import com.example.quiz.domain.Question;
import com.example.quiz.domain.factories.MultipleChoiceQuestionTestFactory;
import com.example.quiz.domain.factories.SingleChoiceQuestionTestFactory;

public class QuizControllerTestFactory {
    public static QuizController createAndStartQuizControllerWithOneSingleChoiceQuestion() {
        Question singleChoiceQuestion = SingleChoiceQuestionTestFactory.createSingleChoiceQuestion();
        InMemoryQuestionRepository inMemoryQuestionRepository = new InMemoryQuestionRepository();
        inMemoryQuestionRepository.save(singleChoiceQuestion);
        QuizService quizService = new QuizService(inMemoryQuestionRepository);
        QuizSessionService quizSessionService = new QuizSessionService(quizService, new InMemoryQuizSessionRepository());
        StubTokenGenerator stubIdGenerator = new StubTokenGenerator();
        return new QuizController(quizSessionService, stubIdGenerator, inMemoryQuestionRepository);
    }

    public static QuizController createAndStartChoiceQuizControllerWithOneMultipleChoiceQuestion() {
        Question multipleChoiceQuestion = MultipleChoiceQuestionTestFactory.multipleChoiceQuestion();
        InMemoryQuestionRepository inMemoryQuestionRepository = new InMemoryQuestionRepository();
        inMemoryQuestionRepository.save(multipleChoiceQuestion);
        QuizService quizService = new QuizService(inMemoryQuestionRepository);
        QuizSessionService quizSessionService = new QuizSessionService(quizService, new InMemoryQuizSessionRepository());
        StubTokenGenerator stubIdGenerator = new StubTokenGenerator();
        return new QuizController(quizSessionService, stubIdGenerator, inMemoryQuestionRepository);
    }
}