package com.example.quiz.application.port;

import com.example.quiz.domain.Quiz;
import com.example.quiz.domain.QuizId;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

public class InMemoryQuizRepository implements QuizRepository {
    List<Quiz> quizList = new ArrayList<>();
    private AtomicLong counter = new AtomicLong();

    @Override
    public Quiz save(Quiz quiz) {
        if (quiz.getId() == null) {
            quiz.setId(QuizId.of(counter.getAndIncrement()));
        }

        quizList.add(quiz);
        return quiz;
    }

    @Override
    public List<Quiz> findAll() {
        return quizList;
    }

    @Override
    public Optional<Quiz> findById(QuizId quizId) {
        return quizList.stream()
                .filter(q -> q.getId().equals(quizId))
                .findFirst();
    }

}
