package com.tomspencerlondon.quiz.hexagon.application;

import com.tomspencerlondon.quiz.hexagon.application.port.QuestionRepository;
import com.tomspencerlondon.quiz.hexagon.domain.Question;

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
