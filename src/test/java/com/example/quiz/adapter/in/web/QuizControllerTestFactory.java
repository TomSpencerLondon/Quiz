package com.example.quiz.adapter.in.web;

import com.example.quiz.adapter.in.web.answer.QuizController;
import com.example.quiz.application.QuizService;
import com.example.quiz.application.QuizSessionService;
import com.example.quiz.application.port.InMemoryQuestionRepository;
import com.example.quiz.domain.Question;
import com.example.quiz.domain.factories.MultipleChoiceQuestionTestFactory;
import com.example.quiz.domain.factories.SingleChoiceQuestionTestFactory;

public class QuizControllerTestFactory {
    public static QuizController createAndStartQuizControllerWithOneSingleChoiceQuestion() {
        Question singleChoiceQuestion = SingleChoiceQuestionTestFactory.createSingleChoiceQuestion();
        InMemoryQuestionRepository inMemoryQuestionRepository = new InMemoryQuestionRepository();
        inMemoryQuestionRepository.save(singleChoiceQuestion);
        QuizService quizService = new QuizService(inMemoryQuestionRepository);
        QuizSessionService quizSessionService = new QuizSessionService(quizService);
        QuizController quizController = new QuizController(quizSessionService);
        quizController.start();
        return quizController;
    }

    public static QuizController createAndStartChoiceQuizControllerWithOneMultipleChoiceQuestion() {
        Question multipleChoiceQuestion = MultipleChoiceQuestionTestFactory.multipleChoiceQuestion();
        InMemoryQuestionRepository inMemoryQuestionRepository = new InMemoryQuestionRepository();
        inMemoryQuestionRepository.save(multipleChoiceQuestion);
        QuizService quizService = new QuizService(inMemoryQuestionRepository);
        QuizSessionService quizSessionService = new QuizSessionService(quizService);
        final QuizController quizController = new QuizController(quizSessionService);
        quizController.start();
        return quizController;
    }
}
