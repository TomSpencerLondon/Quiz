package com.tomspencerlondon.quiz.adapter.in.web.answer;

import com.tomspencerlondon.quiz.hexagon.application.port.InMemoryQuestionRepository;
import com.tomspencerlondon.quiz.hexagon.application.port.QuestionRepository;
import com.tomspencerlondon.quiz.hexagon.domain.Choice;
import com.tomspencerlondon.quiz.hexagon.domain.ChoiceId;

import com.tomspencerlondon.quiz.hexagon.domain.Question;
import com.tomspencerlondon.quiz.hexagon.domain.Response;
import com.tomspencerlondon.quiz.hexagon.domain.domain.QuestionBuilder;

import java.util.List;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ResponseViewTest {
    @Test
    void responseTransformedToResponseView() {
        List<Choice> choices = List.of(
                new Choice(ChoiceId.of(1L), "Answer 1", true),
                new Choice(ChoiceId.of(2L), "Answer 2", false));
        Question question = new QuestionBuilder().withQuestionId(1L).withSingleChoice(choices).build();
        QuestionRepository questionRepository = new InMemoryQuestionRepository();
        questionRepository.save(question);

        Response response = new Response(question.getId(), true, List.of(ChoiceId.of(1L)));

        ResponseView responseView = ResponseView.from(response, questionRepository);

        assertThat(responseView.getChosenAnswers())
                .containsExactly("Answer 1");
    }

    @Test
    @Disabled
    void responseTransformedToResponseViewOld() {
        List<Choice> choices = List.of(
                new Choice(ChoiceId.of(1L), "Answer 1", true),
                new Choice(ChoiceId.of(2L), "Answer 2", false));
        Question question = new QuestionBuilder().withQuestionId(1L).withSingleChoice(choices).build();
        QuestionRepository questionRepository = new InMemoryQuestionRepository();
        questionRepository.save(question);

        Response response = new Response(question.getId(), true, new Choice(ChoiceId.of(1L), "Answer 1", true));

        ResponseView responseView = ResponseView.from(response, questionRepository);

        assertThat(responseView.getChosenAnswers())
                .containsExactly("Answer 1");
    }
}