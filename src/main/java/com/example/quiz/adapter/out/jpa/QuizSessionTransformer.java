package com.example.quiz.adapter.out.jpa;

import com.example.quiz.application.port.ChoiceRepository;
import com.example.quiz.application.port.QuestionRepository;
import com.example.quiz.domain.*;

import java.util.List;

public class QuizSessionTransformer {
    private final QuestionRepository questionRepository;
    private final ChoiceRepository choiceRepository;
    private final ResponseTransformer responseTransformer;

    public QuizSessionTransformer(QuestionRepository questionRepository,
                                  ChoiceRepository choiceRepository,
                                  ResponseTransformer responseTransformer) {
        this.questionRepository = questionRepository;
        this.choiceRepository = choiceRepository;
        this.responseTransformer = responseTransformer;
    }

    QuizSession toQuizSession(QuizSessionDbo quizSessionDbo) {
        QuizSession quizSession = new QuizSession();
        quizSession.setId(QuizSessionId.of(quizSessionDbo.getId()));
        quizSession.setToken(quizSessionDbo.getToken());
        Long currentQuestionId = quizSessionDbo.getCurrentQuestionId();
        Question question = questionRepository
                .findById(QuestionId.of(currentQuestionId))
                .orElseThrow();

        quizSession.setQuestion(question);
        List<Response> responses = quizSessionDbo.getResponses()
                                                 .stream()
                                                 .map(responseTransformer::toResponse)
                                                 .toList();

        responses.forEach(quizSession::setResponse);
        quizSession.setStartedAt(quizSessionDbo.getStartedAt());

        return quizSession;
    }

    QuizSessionDbo toQuizSessionDbo(QuizSession quizSession) {
        QuizSessionDbo quizSessionDbo = new QuizSessionDbo();
        quizSessionDbo.setCurrentQuestionId(quizSession.getQuestion().getId().id());
        quizSessionDbo.setToken(quizSession.getToken());
        quizSessionDbo.setStartedAt(quizSession.getStartedAt());
        List<ResponseDbo> responseDbos = quizSession
                .getResponses()
                .stream().map(responseTransformer::toResponseDbo)
                .toList();

        quizSessionDbo.setResponses(responseDbos);

        return quizSessionDbo;

    }

}
