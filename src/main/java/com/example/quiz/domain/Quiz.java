package com.example.quiz.domain;

import com.example.quiz.application.port.QuestionRepository;

import javax.transaction.Transactional;
import java.util.List;

public class Quiz {

    private final QuestionRepository questionRepository;

    public Quiz(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    @Transactional
    public List<Question> questions() {
        return questionRepository.findAll();
    }

    public QuizSession start() {
        return new QuizSession(this);
    }
}
