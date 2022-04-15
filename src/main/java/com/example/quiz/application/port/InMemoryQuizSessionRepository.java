package com.example.quiz.application.port;

import com.example.quiz.domain.QuizSession;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

public class InMemoryQuizSessionRepository implements QuizSessionRepository {
    List<QuizSession> quizSessionList = new ArrayList<>();
    private AtomicLong counter = new AtomicLong();

    @Override
    public QuizSession save(QuizSession quizSession) {
        if (quizSession.getId() == null) {
            quizSession.setId(counter.getAndIncrement());
        }

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
