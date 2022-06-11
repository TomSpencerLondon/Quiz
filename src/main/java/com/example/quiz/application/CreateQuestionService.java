package com.example.quiz.application;

import com.example.quiz.application.port.QuestionRepository;
import com.example.quiz.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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
