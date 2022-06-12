package com.example.quiz.domain;

import com.example.quiz.application.port.InMemoryQuizRepository;
import com.example.quiz.application.port.QuizRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class QuizBuilder {
    List<QuestionId> questionIds = new ArrayList<>();
    QuizRepository quizRepository = new InMemoryQuizRepository();
    private QuizId quizId;

    public QuizBuilder withQuestions(Question... questions) {
        this.questionIds.addAll(Stream.of(questions).map(Question::getId).toList());
        return this;
    }

    public QuizBuilder withId(long id) {
        this.quizId = QuizId.of(id);
        return this;
    }

    public Quiz save() {
        return quizRepository.save(build());
    }

    public Quiz build() {
        Quiz quiz = new Quiz("Quiz 1", questionIds);
        quiz.setId(quizId);
        return quiz;
    }
}
