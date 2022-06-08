package com.example.quiz.application;

import com.example.quiz.application.port.QuizRepository;
import com.example.quiz.domain.QuestionId;
import com.example.quiz.domain.Quiz;
import com.example.quiz.domain.QuizId;

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
