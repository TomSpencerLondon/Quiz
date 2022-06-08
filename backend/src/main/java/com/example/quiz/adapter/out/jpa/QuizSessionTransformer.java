package com.example.quiz.adapter.out.jpa;

import com.example.quiz.application.port.QuestionRepository;
import com.example.quiz.domain.*;

import java.time.ZonedDateTime;
import java.util.List;

public class QuizSessionTransformer {
    private final QuestionRepository questionRepository;
    private final ResponseTransformer responseTransformer;

    public QuizSessionTransformer(QuestionRepository questionRepository,
                                  ResponseTransformer responseTransformer) {
        this.questionRepository = questionRepository;
        this.responseTransformer = responseTransformer;
    }

    QuizSession toQuizSession(QuizSessionDbo quizSessionDbo) {
        QuizSessionId quizSessionId = QuizSessionId.of(quizSessionDbo.getId());
        String token = quizSessionDbo.getToken();
        Long currentQuestionId = quizSessionDbo.getCurrentQuestionId();
        Question question = questionRepository
                .findById(QuestionId.of(currentQuestionId))
                .orElseThrow();
        List<Response> responses = quizSessionDbo.getResponses()
                                                 .stream()
                                                 .map(responseTransformer::toResponse)
                                                 .toList();
        ZonedDateTime startedAt = quizSessionDbo.getStartedAt();
        return new QuizSession(quizSessionId, token, question.getId(), responses, startedAt);
    }

    QuizSessionDbo toQuizSessionDbo(QuizSession quizSession) {
        QuizSessionDbo quizSessionDbo = new QuizSessionDbo();
        quizSessionDbo.setId(quizSession.getId().id());
        quizSessionDbo.setCurrentQuestionId(quizSession.currentQuestionId().id());
        quizSessionDbo.setToken(quizSession.getToken());
        quizSessionDbo.setStartedAt(quizSession.getStartedAt());

        List<ResponseDbo> responseDbos = quizSession
                .responses()
                .stream()
                .map(responseTransformer::toResponseDbo)
                .toList();

        quizSessionDbo.setResponses(responseDbos);

        return quizSessionDbo;

    }

}
