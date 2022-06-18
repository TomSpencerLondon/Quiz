package com.example.quiz.hexagon.application;

import com.example.quiz.hexagon.application.port.QuestionRepository;
import com.example.quiz.hexagon.domain.Question;

import java.util.List;

public class CreateQuestionService {
    private QuestionRepository questionRepository;

    public CreateQuestionService(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    public List<Question> findAll() {
        return questionRepository.findAll();
    }

    public Question add(Question question) {
        return questionRepository.save(question);
    }
}
