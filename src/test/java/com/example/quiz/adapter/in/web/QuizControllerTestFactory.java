package com.example.quiz.adapter.in.web;

import com.example.quiz.domain.factories.SingleChoiceQuestionTestFactory;
import com.example.quiz.hexagon.application.port.InMemoryQuestionRepository;
import com.example.quiz.hexagon.application.port.InMemoryQuizRepository;
import com.example.quiz.hexagon.application.port.QuestionRepository;
import com.example.quiz.hexagon.application.port.QuizRepository;
import com.example.quiz.hexagon.domain.Question;
import com.example.quiz.hexagon.domain.Quiz;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class QuizControllerTestFactory {

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
}