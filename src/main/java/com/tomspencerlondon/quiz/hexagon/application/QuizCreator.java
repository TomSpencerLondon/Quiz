package com.tomspencerlondon.quiz.hexagon.application;

import com.tomspencerlondon.quiz.hexagon.application.port.QuizRepository;
import com.tomspencerlondon.quiz.hexagon.domain.QuestionId;
import com.tomspencerlondon.quiz.hexagon.domain.Quiz;
import com.tomspencerlondon.quiz.hexagon.domain.QuizId;

import java.util.List;

public class QuizCreator {
    private final QuizRepository quizRepository;

    public QuizCreator(QuizRepository quizRepository) {
        this.quizRepository = quizRepository;
    }

    public QuizId createQuiz(String quizName, List<QuestionId> questionIds) {
        Quiz quiz = quizRepository.save(new Quiz(quizName, questionIds));
        return quiz.getId();
    }
}
