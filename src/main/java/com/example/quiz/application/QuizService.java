package com.example.quiz.application;

import com.example.quiz.application.port.QuestionRepository;
import com.example.quiz.domain.Question;
import com.example.quiz.domain.Quiz;

import java.util.List;

public class QuizService {
    private final QuestionRepository questionRepository;

    public QuizService(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    public Quiz createQuiz() {
        List<Question> questions = questionRepository.findAll();
        return new Quiz(questions);
    }
}