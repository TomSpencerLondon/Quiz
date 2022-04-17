package com.example.quiz.adapter.out.jpa;

import com.example.quiz.application.port.ChoiceRepository;
import com.example.quiz.application.port.QuestionRepository;
import com.example.quiz.domain.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class ResponseTransformer {
    private final ChoiceRepository choiceRepository;
    private final QuestionRepository questionRepository;

    public ResponseTransformer(ChoiceRepository choiceRepository, QuestionRepository questionRepository) {
        this.choiceRepository = choiceRepository;
        this.questionRepository = questionRepository;
    }

    public ResponseDbo toResponseDbo(Response response) {
        ResponseDbo responseDbo = new ResponseDbo();
        responseDbo.setQuestionId(response.questionId().id());
        Set<Long> choiceIds = response
                .choices()
                .stream()
                .map(choice -> choice.getId().id())
                .collect(Collectors.toSet());
        responseDbo.setChoiceIds(choiceIds);

        return responseDbo;
    }

    public Response toResponse(ResponseDbo responseDbo) {
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
