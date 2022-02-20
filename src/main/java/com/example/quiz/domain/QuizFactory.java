package com.example.quiz.domain;

import com.example.quiz.application.port.QuestionRepository;

import java.util.List;

public class QuizFactory {

    private final QuestionRepository questionRepository;

    public QuizFactory(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    public Quiz createQuiz() {
        List<Question> questions = questionRepository.findAll();
        return new Quiz(questions);
    }
}
