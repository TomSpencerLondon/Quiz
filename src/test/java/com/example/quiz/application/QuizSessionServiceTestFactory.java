package com.example.quiz.application;

import com.example.quiz.hexagon.application.QuizService;
import com.example.quiz.hexagon.application.QuizSessionService;
import com.example.quiz.hexagon.application.port.InMemoryQuestionRepository;
import com.example.quiz.hexagon.application.port.InMemoryQuizSessionRepository;
import com.example.quiz.hexagon.domain.Question;
import com.example.quiz.domain.factories.SingleChoiceQuestionTestFactory;

public class QuizSessionServiceTestFactory {
    public static QuizSessionService createQuizSessionService() {
        Question singleChoiceQuestion = SingleChoiceQuestionTestFactory.createSingleChoiceQuestion();
        InMemoryQuestionRepository inMemoryQuestionRepository = new InMemoryQuestionRepository();
        inMemoryQuestionRepository.save(singleChoiceQuestion);
        QuizService quizService = new QuizService(inMemoryQuestionRepository);
        QuizSessionService quizSessionService = new QuizSessionService(new InMemoryQuizSessionRepository(), null, null);
        return quizSessionService;
    }
}
