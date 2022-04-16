package com.example.quiz.adapter.out.jpa;

import com.example.quiz.application.port.QuestionRepository;
import com.example.quiz.domain.Question;
import com.example.quiz.domain.QuizSession;
import com.example.quiz.domain.QuizSessionId;
import com.example.quiz.domain.Response;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public class QuizSessionTransformer {
    private final QuestionRepository questionRepository;

    public QuizSessionTransformer(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    QuizSession toQuizSession(QuizSessionDbo quizSessionDbo) {
        QuizSession quizSession = new QuizSession();
        quizSession.setId(QuizSessionId.of(quizSessionDbo.getId()));
        Long currentQuestionId = quizSessionDbo.getCurrentQuestionId();
        List<Question> questions = questionRepository.findAll();
        Optional<Question> question = questions.stream()
                                               .filter(q -> q.getId().id() == currentQuestionId)
                                               .findFirst();
        question.ifPresent(quizSession::setQuestion);
        List<ResponseDbo> responseDbos = quizSessionDbo.getResponses();
        List<Response> responses = responseDbos.stream().map(this::toResponse).toList();

        return null;
    }

    QuizSessionDbo toQuizSessionDbo(QuizSession quizSession) {
        throw new UnsupportedOperationException("Implement me!");
    }

    private Response toResponse(ResponseDbo responseDbo) {
        Set<Long> choiceIds = responseDbo.getChoiceIds();
        List<Question> questions = questionRepository.findAll().stream().toList();

        questions.stream().map(q -> q.choices()).toList();
        return null;
    }
}
