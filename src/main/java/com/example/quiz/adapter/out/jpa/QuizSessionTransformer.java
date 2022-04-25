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
        QuizSession quizSession = new QuizSession();
        QuizSessionId quizSessionId = QuizSessionId.of(quizSessionDbo.getId());
        quizSession.setId(quizSessionId);
        String token = quizSessionDbo.getToken();
        quizSession.setToken(token);
        Long currentQuestionId = quizSessionDbo.getCurrentQuestionId();
        Question question = questionRepository
                .findById(QuestionId.of(currentQuestionId))
                .orElseThrow();

        quizSession.setQuestion(question);
        List<Response> responses = quizSessionDbo.getResponses()
                                                 .stream()
                                                 .map(responseTransformer::toResponse)
                                                 .toList();

        responses.forEach(quizSession::addResponse);
        ZonedDateTime startedAt = quizSessionDbo.getStartedAt();
        quizSession.setStartedAt(startedAt);

        return new QuizSession(quizSessionId, token, question, responses, startedAt);
    }

    QuizSessionDbo toQuizSessionDbo(QuizSession quizSession) {
        QuizSessionDbo quizSessionDbo = new QuizSessionDbo();
        quizSessionDbo.setId(quizSession.getId().id());
        quizSessionDbo.setCurrentQuestionId(quizSession.getQuestion().getId().id());
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
