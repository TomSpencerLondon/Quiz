package com.example.quiz.adapter.out.jpa;

import com.example.quiz.application.port.ChoiceRepository;
import com.example.quiz.application.port.QuestionRepository;
import com.example.quiz.domain.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class QuizSessionTransformer {
    private final QuestionRepository questionRepository;
    private final ChoiceRepository choiceRepository;

    public QuizSessionTransformer(QuestionRepository questionRepository, ChoiceRepository choiceRepository) {
        this.questionRepository = questionRepository;
        this.choiceRepository = choiceRepository;
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
                                                 .map(this::toResponse)
                                                 .toList();

        responses.forEach(quizSession::setResponse);
        quizSession.setStartedAt(quizSessionDbo.getStartedAt());

        return quizSession;
    }

    QuizSessionDbo toQuizSessionDbo(QuizSession quizSession) {
        throw new UnsupportedOperationException("Implement me!");
    }

    private Response toResponse(ResponseDbo responseDbo) {
        Set<Long> choiceIds = responseDbo.getChoiceIds();
        List<Choice> choiceEntries = new ArrayList<>();
        choiceIds.forEach(choiceId -> {
            Choice choice = choiceRepository
                    .findById(ChoiceId.of(choiceId))
                    .orElseThrow();
            choiceEntries.add(choice);
        });

        Choice[] choices = choiceEntries.toArray(new Choice[]{});
        QuestionId questionId = QuestionId.of(responseDbo.getQuestionId());
        Question question = questionRepository
                .findById(questionId)
                .orElseThrow();

        return new Response(questionId, question.isCorrectAnswer(choices), choices);
    }
}
