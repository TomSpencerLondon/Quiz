package com.tomspencerlondon.quiz.adapter.in.web.answer;

import com.tomspencerlondon.quiz.hexagon.domain.Question;
import com.tomspencerlondon.quiz.hexagon.domain.domain.QuestionBuilder;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class AskQuestionFormTest {
    @Test
    void convertsQuestionToAskSingleChoiceForm() {
        // given
        Question question = new QuestionBuilder()
                .withDefaultSingleChoice()
                .build();

        // when
        AskQuestionForm form = AskQuestionForm.from(question);

        // then
        assertThat(form.getQuestion())
                .isEqualTo("Question 1");
        assertThat(form.getChoices())
                .containsExactly(
                        new ChoiceSelection(1L, "Answer 1"),
                        new ChoiceSelection(2L, "Answer 2"),
                        new ChoiceSelection(3L, "Answer 3"));
    }
}