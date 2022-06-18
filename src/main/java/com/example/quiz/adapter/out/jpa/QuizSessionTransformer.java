package com.example.quiz.adapter.out.jpa;

import com.example.quiz.hexagon.application.port.QuestionRepository;
import com.example.quiz.hexagon.domain.*;

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
        // TODO: Decide what to do with responses and started at - do we add them to the QuizSession
        List<Response> responses = quizSessionDbo.getResponses()
                                                 .stream()
                                                 .map(responseTransformer::toResponse)
                                                 .toList();
        ZonedDateTime startedAt = quizSessionDbo.getStartedAt();
        QuizSession quizSession = new QuizSession(QuestionId.of(currentQuestionId), token, QuizId.of(quizSessionDbo.getQuizId()));
        quizSession.setId(quizSessionId);
        return quizSession;
    }

    QuizSessionDbo toQuizSessionDbo(QuizSession quizSession) {
        QuizSessionDbo quizSessionDbo = new QuizSessionDbo();
        quizSessionDbo.setId(quizSession.getId().id());
        quizSessionDbo.setCurrentQuestionId(quizSession.currentQuestionId().id());
        quizSessionDbo.setToken(quizSession.getToken());
        quizSessionDbo.setStartedAt(quizSession.getStartedAt());
        quizSessionDbo.setQuizId(quizSession.quizId().id());

        List<ResponseDbo> responseDbos = quizSession
                .responses()
                .stream()
                .map(responseTransformer::toResponseDbo)
                .toList();

        quizSessionDbo.setResponses(responseDbos);

        return quizSessionDbo;

    }

}
