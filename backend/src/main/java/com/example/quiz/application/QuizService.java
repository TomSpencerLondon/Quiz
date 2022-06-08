package com.example.quiz.application;

import com.example.quiz.application.port.QuestionRepository;
import com.example.quiz.domain.Question;
import com.example.quiz.domain.QuestionId;
import com.example.quiz.domain.Quiz;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class QuizService {
    private final QuestionRepository questionRepository;

    public QuizService(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    public Quiz createQuiz() {
        AtomicInteger quizNumber = new AtomicInteger();
        List<Question> questions = questionRepository.findAll();
        List<QuestionId> questionIds = questions.stream()
                                                .map(Question::getId)
                                                .toList();
        return new Quiz("Quiz " + quizNumber.getAndIncrement(), questionIds);
    }
}