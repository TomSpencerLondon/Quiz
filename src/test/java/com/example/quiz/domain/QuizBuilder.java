package com.example.quiz.domain;

import com.example.quiz.application.port.InMemoryQuizRepository;
import com.example.quiz.application.port.QuizRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class QuizBuilder {
    List<QuestionId> questionIds = new ArrayList<>();
    QuizRepository questionRepository = new InMemoryQuizRepository();

    public QuizBuilder withQuestions(Question... questions) {
        this.questionIds.addAll(Stream.of(questions).map(Question::getId).toList());
        return this;
    }

    public Quiz save() {
        return questionRepository.save(build());
    }

    public Quiz build() {
        return new Quiz("Quiz 1", questionIds);
    }
}
