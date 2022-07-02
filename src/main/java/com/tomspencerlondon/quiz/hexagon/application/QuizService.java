package com.tomspencerlondon.quiz.hexagon.application;

import com.tomspencerlondon.quiz.hexagon.application.port.QuestionRepository;
import com.tomspencerlondon.quiz.hexagon.domain.Question;
import com.tomspencerlondon.quiz.hexagon.domain.QuestionId;
import com.tomspencerlondon.quiz.hexagon.domain.Quiz;

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