package com.example.quiz.application.port;

import com.example.quiz.domain.QuizSession;

import java.util.ArrayList;
import java.util.List;

public class InMemoryQuizSessionRepository implements QuizSessionRepository {
    List<QuizSession> quizSessionList = new ArrayList<>();
    Long id = 0L;

    @Override
    public QuizSession save(QuizSession quizSession) {
        quizSession.setId(id++);
        quizSessionList.add(quizSession);
        return quizSession;
    }

    public QuizSession findByToken(String token) {
        return quizSessionList.stream()
                              .filter(q -> token.equals(q.getToken()))
                              .findFirst()
                              .get();
    }
}
