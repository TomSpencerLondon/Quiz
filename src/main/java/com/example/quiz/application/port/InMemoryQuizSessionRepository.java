package com.example.quiz.application.port;

import com.example.quiz.domain.QuizSession;

import java.util.ArrayList;
import java.util.List;

public class InMemoryQuizSessionRepository implements QuizSessionRepository {
    List<QuizSession> quizSessionList = new ArrayList<>();
    @Override
    public QuizSession save(QuizSession quizSession) {
        quizSessionList.add(quizSession);
        return quizSession;
    }

    @Override
    public QuizSession findById(String id) {
        return quizSessionList.stream().filter(q -> id.equals(q.getId())).findFirst().get();
    }
}
