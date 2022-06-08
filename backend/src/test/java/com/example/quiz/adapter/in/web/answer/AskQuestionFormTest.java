package com.example.quiz.adapter.in.web.answer;

import com.example.quiz.domain.Choice;
import com.example.quiz.domain.ChoiceId;
import com.example.quiz.domain.Question;
import com.example.quiz.domain.SingleChoice;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class AskQuestionFormTest {
    @Test
    void convertsQuestionToAskSingleChoiceForm() {
        // given
        Question singleChoiceQuestion = new Question(
                "Question 1",
                new SingleChoice(List.of(new Choice(ChoiceId.of(73L), "Answer 1", true), new Choice(ChoiceId.of(44L), "Answer 2", false))));

        // when
        AskQuestionForm form = AskQuestionForm.from(singleChoiceQuestion);

        // then
        assertThat(form.getQuestion())
                .isEqualTo("Question 1");
        assertThat(form.getChoices())
                .containsExactly(new ChoiceSelection(73L, "Answer 1"), new ChoiceSelection(44L, "Answer 2"));
    }
}